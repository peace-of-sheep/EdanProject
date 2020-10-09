package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.api.ApiResponse;
import tech.ankainn.edanapplication.api.GaldosService;
import tech.ankainn.edanapplication.api.ReniecService;
import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.db.FormTwoDao;
import tech.ankainn.edanapplication.model.api.DataResponse;
import tech.ankainn.edanapplication.model.api.ReniecData;
import tech.ankainn.edanapplication.model.api.formtwo.FormTwoRemote;
import tech.ankainn.edanapplication.model.app.formTwo.HouseholdData;
import tech.ankainn.edanapplication.model.dto.FormTwoCompleteEntity;
import tech.ankainn.edanapplication.model.dto.FormTwoEntity;
import tech.ankainn.edanapplication.model.dto.LivelihoodEntity;
import tech.ankainn.edanapplication.model.dto.MemberEntity;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.dto.FormTwoSubset;
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;
import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.model.factory.FormTwoFactory;
import tech.ankainn.edanapplication.api.EdanApiService;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class FormTwoRepository {

    private static final LatLng defaultLatLng = new LatLng(-7.146,-75.009);

    private static FormTwoRepository instance;

    private AppExecutors appExecutors;

    private EdanApiService apiService;
    private ReniecService reniecService;
    private GaldosService galdosService;

    private FormTwoDao formTwoDao;
    private Cache cache;

    public static FormTwoRepository getInstance(AppExecutors appExecutors,
                                                EdanApiService apiService,
                                                ReniecService reniecService,
                                                GaldosService galdosService,
                                                EdanDatabase edanDatabase,
                                                Cache cache) {
        if(instance == null) {
            synchronized (FormTwoRepository.class) {
                if(instance == null) {
                    instance = new FormTwoRepository(appExecutors, apiService, reniecService, galdosService, edanDatabase, cache);
                }
            }
        }
        return instance;
    }

    private FormTwoRepository(AppExecutors appExecutors, EdanApiService apiService, ReniecService reniecService, GaldosService galdosService, EdanDatabase edanDatabase, Cache cache) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
        this.reniecService = reniecService;
        this.galdosService = galdosService;
        this.cache = cache;
        formTwoDao = edanDatabase.formTwoDao();
    }

    public LiveData<List<FormTwoSubset>> loadAllFormTwoSubset() {
        return formTwoDao.loadAllFormTwoSubset();
    }

    public LiveData<HouseholdData> loadHouseholdData() {
        LiveData<FormTwoData> source = cache.getFormTwoData();
        MediatorLiveData<HouseholdData> result = new MediatorLiveData<>();
        result.addSource(source, formTwoData -> {
            if (formTwoData != null) {
                result.setValue(formTwoData.householdData);
            }
        });
        return result;
    }

    public LiveData<List<MemberData>> loadListMemberData() {
        LiveData<FormTwoData> source = cache.getFormTwoData();
        MediatorLiveData<List<MemberData>> result = new MediatorLiveData<>();
        result.addSource(source, formTwoData -> {
            if (formTwoData != null) {
                result.setValue(formTwoData.listMemberData);
            }
        });
        return result;
    }

    public LiveData<MemberData> loadCurrentMemberData() {
        LiveData<MemberData> source = cache.getMemberData();
        MediatorLiveData<MemberData> result = new MediatorLiveData<>();
        result.addSource(source, memberData -> {
            if (memberData != null) {
                result.setValue(memberData);
            }
        });
        return result;
    }

    public LiveData<ReniecData> searchPersonData(String dni) {
        MutableLiveData<ReniecData> liveData = new MutableLiveData<>();
        reniecService.getPersonByDniNumber(dni).enqueue(new Callback<ReniecData>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ReniecData> call, Response<ReniecData> response) {
                ReniecData reniecData = response.body();
                if (reniecData == null || reniecData.nombres == null || reniecData.apellidos == null) {
                    liveData.postValue(null);
                } else {
                    liveData.postValue(reniecData);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ReniecData> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public void saveCacheMemberData(MemberData memberData) {
        cache.setMemberData(memberData);
    }

    public void clearCacheMemberData() {
        cache.setMemberData(null);
    }

    public void pushCacheMemberData(MemberData currentMemberData) {
        FormTwoData formTwoData = cache.getFormTwoData().getValue();
        if (formTwoData != null) {
            List<MemberData> list = new ArrayList<>(formTwoData.listMemberData);
            if (currentMemberData.tempId == 0L) {
                list.add(currentMemberData);
            } else {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).tempId == currentMemberData.tempId) {
                        list.set(i, currentMemberData);
                    }
                }
            }
            formTwoData.listMemberData = list;
            cache.setFormTwoData(formTwoData);
        }
    }

    public void uploadFormTwoById(long formTwoId) {
        appExecutors.networkIO().execute(() -> {
            FormTwoCompleteEntity formTwoCompleteData = formTwoDao.loadFormTwoById(formTwoId);

            if (formTwoCompleteData.formTwoEntity.formTwoApiId != -1) {
                return;
            }

            FormTwoRemote formTwoRemote = FormTwoFactory.completeEntityToRemote(formTwoCompleteData);

            Timber.tag(Tagger.DUMPER).d("uploadFormTwoById: %s", new Gson().toJson(formTwoRemote));

            galdosService.postFormTwo(formTwoRemote).enqueue(new Callback<ApiResponse<DataResponse>>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<ApiResponse<DataResponse>> call, Response<ApiResponse<DataResponse>> response) {

                    if (response.code() == 201) {
                        appExecutors.diskIO().execute(() -> {
                            Integer apiId = response.body().getData().getFORM2ACABID();

                            FormTwoEntity entity = formTwoCompleteData.formTwoEntity;
                            entity.formTwoApiId = apiId;
                            formTwoDao.updateFormTwo(entity);
                        });
                    }
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<ApiResponse<DataResponse>> call, Throwable t) {
                    Timber.tag(Tagger.DUMPER).e(t);
                }
            });
        });
    }

    public void loadFormTwoData(long id) {
        if(cache.getFormTwoData().getValue() != null)
            return;

        if (id == 0L) {
            createFormTwoData();
        } else {
            loadFormTwoDataById(id);
        }
    }

    private void createFormTwoData() {
        FormTwoData formTwoData = FormTwoFactory.createEmptyFormTwoData();

        GenInfData genInfData = cache.getGenInfData().getValue();
        if (genInfData == null) {
            formTwoData.genInfData.mapLocationData.latitude = defaultLatLng.latitude;
            formTwoData.genInfData.mapLocationData.longitude = defaultLatLng.longitude;

            cache.setGenInfData(formTwoData.genInfData);
        } else {
            formTwoData.genInfData = genInfData;
        }

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String dateCreation = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);
        String hourCreation = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);

        formTwoData.genInfData.headerData.dateCreation = dateCreation;
        formTwoData.genInfData.headerData.hourCreation = hourCreation;

        cache.setFormTwoData(formTwoData);
    }

    private void loadFormTwoDataById(long id) {
        appExecutors.diskIO().execute(() -> {

            FormTwoCompleteEntity source = formTwoDao.loadFormTwoById(id);
            FormTwoData formTwoData = FormTwoFactory.dataFromEntityComplete(source);

            cache.setFormTwoData(formTwoData);
            cache.setGenInfData(formTwoData.genInfData);
        });
    }

    public void saveForm() {
        FormTwoData formTwoData = cache.getFormTwoData().getValue();
        if (formTwoData == null) {
            return;
        }

        appExecutors.diskIO().execute(() -> {

            formTwoData.dataVersion++;

            Timber.tag(Tagger.DUMPER).d("FormTwoRepository.saveForm: %s", new Gson().toJson(formTwoData));

            FormTwoEntity formTwoEntity = FormTwoFactory.dataToEntity(formTwoData);

            List<MemberEntity> listMemberResult = new ArrayList<>();
            List<MemberData> listMember = formTwoData.listMemberData;
            if (listMember != null && listMember.size() > 0) {
                for (MemberData memberData : listMember) {
                    MemberEntity memberEntity = FormTwoFactory.dataToEntity(memberData);
                    listMemberResult.add(memberEntity);
                }
            }

            List<LivelihoodEntity> listLivelihoodResult = new ArrayList<>();
            List<LivelihoodData> listLivelihood = formTwoData.listLivelihood;
            if (listLivelihood != null && listLivelihood.size() > 0) {
                for (LivelihoodData livelihoodData : listLivelihood) {
                    LivelihoodEntity livelihoodEntity = FormTwoFactory.dataToEntity(livelihoodData);
                    listLivelihoodResult.add(livelihoodEntity);
                }
            }

            if (formTwoEntity.formTwoId == 0) {
                formTwoDao.insertFormTwoComplete(formTwoEntity, listMemberResult, listLivelihoodResult);
            } else {
                formTwoDao.updateFormTwoComplete(formTwoEntity, listMemberResult, listLivelihoodResult);
            }
        });
    }

    public void clearForm() {
        cache.setFormTwoData(null);
    }
}
