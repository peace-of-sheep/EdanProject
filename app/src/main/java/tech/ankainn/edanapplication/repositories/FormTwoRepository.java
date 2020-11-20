package tech.ankainn.edanapplication.repositories;

import androidx.annotation.WorkerThread;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.model.api.ApiResponse;
import tech.ankainn.edanapplication.api.GaldosService;
import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.db.FormTwoDao;
import tech.ankainn.edanapplication.model.api.formtwo.DataResponse;
import tech.ankainn.edanapplication.model.app.formTwo.HouseholdData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.model.dto.FormTwoComplete;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.dto.FormTwoSubset;
import tech.ankainn.edanapplication.util.CombinedLiveData;
import tech.ankainn.edanapplication.util.Utilities;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class FormTwoRepository {

    private static final LatLng defaultLatLng = new LatLng(-11.37621439, -75.41892405);

    private static FormTwoRepository instance;

    private AppExecutors appExecutors;

    private GaldosService galdosService;

    private FormTwoDao formTwoDao;
    private Cache cache;

    public static FormTwoRepository getInstance(AppExecutors appExecutors,
                                                GaldosService galdosService,
                                                EdanDatabase edanDatabase,
                                                Cache cache) {
        if (instance == null) {
            synchronized (FormTwoRepository.class) {
                if (instance == null) {
                    instance = new FormTwoRepository(appExecutors, galdosService, edanDatabase, cache);
                }
            }
        }
        return instance;
    }

    private FormTwoRepository(AppExecutors appExecutors, GaldosService galdosService, EdanDatabase edanDatabase, Cache cache) {
        this.appExecutors = appExecutors;
        this.galdosService = galdosService;
        this.cache = cache;
        formTwoDao = edanDatabase.formTwoDao();
    }

    @SuppressWarnings("ConstantConditions")
    public LiveData<List<FormTwoSubset>> loadAllFormTwoSubset(long userId) {
        LiveData<List<FormTwoSubset>> source = formTwoDao.loadAllFormTwoSubset(userId);
        LiveData<List<Pair<Long, Boolean>>> listSource = cache.getFormTwoLoading();

        return new CombinedLiveData<>(source, listSource, (forms, loadings) -> {

            if (forms == null || loadings == null || loadings.isEmpty()) {
                return forms;
            }

            List<FormTwoSubset> newListForms = new ArrayList<>();
            List<Pair<Long, Boolean>> newListLoadings = new ArrayList<>(loadings);


            for (int i = 0; i < forms.size(); i++) {
                FormTwoSubset formTwoSubset = Utilities.clonePojo(forms.get(i));

                Pair<Long, Boolean> loadingPair = newListLoadings.isEmpty() ? null : newListLoadings.remove(0);

                if (loadingPair == null) {
                    loadings.add(Pair.create(formTwoSubset.id, formTwoSubset.loading));

                } else {
                    while (formTwoSubset.id < loadingPair.first) {
                        loadingPair = newListLoadings.remove(0);
                        loadings.remove(0);
                    }

                    if (formTwoSubset.id > loadingPair.first) {
                        newListLoadings.add(i, loadingPair);
                        loadings.add(i, Pair.create(formTwoSubset.id, formTwoSubset.loading));
                    }

                    if (formTwoSubset.id == loadingPair.first) {
                        formTwoSubset.loading = loadingPair.second;
                    }
                }

                newListForms.add(formTwoSubset);
            }

            return newListForms;
        });
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

    public void loadFormTwoData(long id, long userId, String username) {
        FormTwoData oldForm = cache.getFormTwoData().getValue();
        if (oldForm != null && oldForm.id == id) return;

        appExecutors.diskIO().execute(() -> {

            FormTwoData formTwoData = id == 0L ? createFormTwoData(userId, username) : loadFormTwoDataById(id);

            cache.setFormTwoData(formTwoData);
            cache.setGenInfData(formTwoData.genInfData);
        });
    }

    @WorkerThread
    private FormTwoData createFormTwoData(long userId, String username) {
        FormTwoData formTwoData = Utilities.createEmptyFormTwoData();

        formTwoData.ownerUserId = userId;
        formTwoData.username = username;

        /*GenInfData genInfData = cache.getGenInfData().getValue();
        if (genInfData == null) {
            formTwoData.genInfData.mapLocationData.latitude = defaultLatLng.latitude;
            formTwoData.genInfData.mapLocationData.longitude = defaultLatLng.longitude;

            cache.setGenInfData(formTwoData.genInfData);
        } else {
            formTwoData.genInfData = genInfData;
        }*/

        formTwoData.genInfData.mapLocationData.latitude = defaultLatLng.latitude;
        formTwoData.genInfData.mapLocationData.longitude = defaultLatLng.longitude;

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String dateCreation = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);
        String hourCreation = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);

        formTwoData.genInfData.headerData.dateEvent = dateCreation;
        formTwoData.genInfData.headerData.hourEvent = hourCreation;

        formTwoData.genInfData.headerData.dateCreation = dateCreation;
        formTwoData.genInfData.headerData.hourCreation = hourCreation;

        Timber.tag(Tagger.DATA_FLOW).i("FormTwoRepository.createFormTwoData: %s", formTwoData);

        /*cache.setFormTwoData(formTwoData);*/

        return formTwoData;
    }

    @WorkerThread
    private FormTwoData loadFormTwoDataById(long id) {
        FormTwoComplete source = formTwoDao.loadFormTwoById(id);

        FormTwoData formTwoData = Utilities.dataFromEntityComplete(source);

        Timber.tag(Tagger.DATA_FLOW).i("FormTwoRepository.loadFormTwoDataById: %s", formTwoData);

        /*cache.setFormTwoData(formTwoData);
        cache.setGenInfData(formTwoData.genInfData);*/

        return formTwoData;
    }

    public LiveData<Boolean> uploadFormTwoById(final long formTwoId) {

        MutableLiveData<Boolean> result = new MutableLiveData<>();

        appExecutors.diskIO().execute(() -> {
            FormTwoComplete formTwoComplete = formTwoDao.loadFormTwoById(formTwoId);

            if (formTwoComplete == null || formTwoComplete.formTwoData.formTwoApiId != -1) {
                result.postValue(false);
                return;
            }

            saveLoading(cache, formTwoId, true);

            FormTwoData formTwoData = Utilities.dataFromEntityComplete(formTwoComplete);
            Timber.tag(Tagger.DATA_FLOW).i("FormTwoRepository.uploadFormTwoById: %s", formTwoData);
            /*FormTwoRemote formTwoRemote = Utilities.completeEntityToRemote(formTwoComplete);

            Timber.tag(Tagger.DATA_FLOW).i("FormTwoRepository.uploadFormTwoById: %s", formTwoRemote);*/


            galdosService.postFormTwo(formTwoData).enqueue(new Callback<ApiResponse<DataResponse>>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<ApiResponse<DataResponse>> call, Response<ApiResponse<DataResponse>> response) {
                    appExecutors.diskIO().execute(() -> {
                        if (response.body() != null && response.body().getData() != null && response.body().getData().getFORM2ACABID() != null) {
                            Integer apiId = response.body().getData().getFORM2ACABID();

                            /*FormTwoData formTwoData = formTwoComplete.formTwoData;*/
                            formTwoData.formTwoApiId = apiId;
                            formTwoDao.updateFormTwo(formTwoData);
                            result.postValue(true);
                        } else {
                            result.postValue(false);
                        }

                        saveLoading(cache, formTwoId, false);
                    });
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<ApiResponse<DataResponse>> call, Throwable t) {
                    result.postValue(false);

                    saveLoading(cache, formTwoId, false);
                }
            });
        });
        return result;
    }

    public void saveForm() {
        FormTwoData formTwoData = cache.getFormTwoData().getValue();
        if (formTwoData == null) {
            return;
        }
        appExecutors.diskIO().execute(() -> {
            formTwoData.dataVersion++;

            setHouseholdConditionForMembers(formTwoData);

            Timber.tag(Tagger.DATA_FLOW).i("FormTwoRepository.saveForm: %s", formTwoData);

            if (formTwoData.id == 0) {
                formTwoDao.insertFormTwoComplete(formTwoData);
            } else {
                formTwoDao.updateFormTwoComplete(formTwoData);
            }
        });
    }

    public void clearForm() {
        cache.setGenInfData(null);
        cache.setFormTwoData(null);
    }

    public void removeForTwo(long formTwoId) {
        appExecutors.diskIO().execute(() -> formTwoDao.deleteFormTwo(formTwoId));
    }

    private void saveLoading(Cache cache, long id, boolean loading) {
        List<Pair<Long, Boolean>> loadings = cache.getFormTwoLoading().getValue();

        if (loadings == null) {
            List<Pair<Long, Boolean>> newLoadings = new ArrayList<>();
            newLoadings.add(Pair.create(id, loading));
            cache.setFormTwoLoading(newLoadings);
        } else {
            int size = loadings.size();
            for (int i = 0; i < size; i++) {
                if (Objects.equals(loadings.get(i).first, id)) {
                    loadings.set(i, Pair.create(id, loading));
                }
            }
            cache.setFormTwoLoading(loadings);
        }
    }

    private void setHouseholdConditionForMembers(FormTwoData formTwoData) {
        for (MemberData memberData : formTwoData.memberDataList) {
            memberData.condition = selectConditionByCode(formTwoData.householdData.codeConditionHouse);
            memberData.codeCondition = selectCodeConditionByCode(formTwoData.householdData.codeConditionHouse);
        }
    }
    private String selectConditionByCode(Integer condition) {
        if (condition == 0 || condition == 1) {
            return "Afectado";
        } else if (condition == 2 || condition == 3) {
            return "Damnificado";
        } else {
            return "";
        }
    }
    private String selectCodeConditionByCode(Integer condition) {
        if (condition == 0 || condition == 1) {
            return "2";
        } else if (condition == 2 || condition == 3) {
            return "1";
        } else {
            return "";
        }
    }
}
