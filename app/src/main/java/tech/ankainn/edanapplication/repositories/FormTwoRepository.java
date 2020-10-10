package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
import tech.ankainn.edanapplication.model.api.formtwo.FormTwoRemote;
import tech.ankainn.edanapplication.model.app.formTwo.HouseholdData;
import tech.ankainn.edanapplication.model.dto.FormTwoComplete;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.dto.FormTwoSubset;
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;
import tech.ankainn.edanapplication.model.factory.ModelFactory;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class FormTwoRepository {

    private static final LatLng defaultLatLng = new LatLng(-7.146,-75.009);

    private static FormTwoRepository instance;

    private AppExecutors appExecutors;

    private GaldosService galdosService;

    private FormTwoDao formTwoDao;
    private Cache cache;

    public static FormTwoRepository getInstance(AppExecutors appExecutors,
                                                GaldosService galdosService,
                                                EdanDatabase edanDatabase,
                                                Cache cache) {
        if(instance == null) {
            synchronized (FormTwoRepository.class) {
                if(instance == null) {
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

    public void loadFormTwoData(long id) {
        if (cache.getFormTwoData().getValue() != null)
            return;

        if (id == 0L) {
            createFormTwoData();
        } else {
            loadFormTwoDataById(id);
        }
    }

    private void createFormTwoData() {
        FormTwoData formTwoData = ModelFactory.createEmptyFormTwoData();
        Timber.tag(Tagger.DUMPER).w("FormTwoRepository.createFormTwoData: %s", formTwoData);

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

            FormTwoComplete source = formTwoDao.loadFormTwoById(id);
            Timber.tag(Tagger.DUMPER).w("FormTwoRepository.loadFormTwoDataById: %s", source);

            FormTwoData formTwoData = ModelFactory.dataFromEntityComplete(source);
            Timber.tag(Tagger.DUMPER).w("FormTwoRepository.loadFormTwoDataById: %s", formTwoData);

            cache.setFormTwoData(formTwoData);
            cache.setGenInfData(formTwoData.genInfData);
        });
    }

    public void uploadFormTwoById(long formTwoId) {
        appExecutors.networkIO().execute(() -> {
            FormTwoComplete formTwoComplete = formTwoDao.loadFormTwoById(formTwoId);

            if (formTwoComplete.formTwoData.formTwoApiId != -1) {
                return;
            }

            FormTwoRemote formTwoRemote = ModelFactory.completeEntityToRemote(formTwoComplete);

            Timber.tag(Tagger.DUMPER).d("uploadFormTwoById: %s", new Gson().toJson(formTwoRemote));

            galdosService.postFormTwo(formTwoRemote).enqueue(new Callback<ApiResponse<DataResponse>>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<ApiResponse<DataResponse>> call, Response<ApiResponse<DataResponse>> response) {

                    if (response.code() == 201) {
                        appExecutors.diskIO().execute(() -> {
                            Integer apiId = response.body().getData().getFORM2ACABID();

                            FormTwoData entity = formTwoComplete.formTwoData;
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

    public void saveForm() {
        FormTwoData formTwoData = cache.getFormTwoData().getValue();
        if (formTwoData == null) {
            return;
        }
        appExecutors.diskIO().execute(() -> {
            formTwoData.dataVersion++;

            Timber.tag(Tagger.DUMPER).d("FormTwoRepository.saveForm: %s", new Gson().toJson(formTwoData));

            FormTwoComplete formTwoComplete = FormTwoComplete.create(formTwoData, formTwoData.listMemberData, formTwoData.listLivelihood);

            if (formTwoData.id == 0) {
                formTwoDao.insertFormTwoComplete(formTwoComplete);
            } else {
                formTwoDao.updateFormTwoComplete(formTwoComplete);
            }
        });
    }

    public void clearForm() {
        cache.setFormTwoData(null);
    }
}
