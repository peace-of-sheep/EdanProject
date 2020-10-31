package tech.ankainn.edanapplication.repositories;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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
import tech.ankainn.edanapplication.util.CombinedLiveData;
import tech.ankainn.edanapplication.util.Utilities;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class FormTwoRepository {

    private static final LatLng defaultLatLng = new LatLng(-11.37621439,-75.41892405);

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
        LiveData<List<FormTwoSubset>> source = formTwoDao.loadAllFormTwoSubset();
        LiveData<List<Long>> loadingIds = cache.getFormTwoLoading();

        return new CombinedLiveData<>(source, loadingIds, (forms, ids) -> {

            List<Long> tempIds = new ArrayList<>(ids);
            List<FormTwoSubset> result = new ArrayList<>();

            if (forms == null) return result;

            for (FormTwoSubset form : forms) {

                FormTwoSubset toAdd = form;

                Iterator<Long> iter = tempIds.iterator();
                while (iter.hasNext()) {
                    Long id = iter.next();
                    if (id == form.id) {
                        iter.remove();
                        toAdd = Utilities.clonePojo(form);
                        toAdd.loading = true;
                        break;
                    }
                }

                result.add(toAdd);
            }

            return result;
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
        FormTwoData formTwoData = Utilities.createEmptyFormTwoData();

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
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String dateCreation = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);
        String hourCreation = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);

        formTwoData.genInfData.headerData.dateCreation = dateCreation;
        formTwoData.genInfData.headerData.hourCreation = hourCreation;

        Timber.tag(Tagger.DUMPER).i("FormTwoRepository.createFormTwoData: %s", formTwoData);

        cache.setFormTwoData(formTwoData);
    }

    private void loadFormTwoDataById(long id) {
        appExecutors.diskIO().execute(() -> {
            FormTwoComplete source = formTwoDao.loadFormTwoById(id);

            FormTwoData formTwoData = Utilities.dataFromEntityComplete(source);

            Timber.tag(Tagger.DUMPER).i("FormTwoRepository.loadFormTwoDataById: %s", formTwoData);

            cache.setFormTwoData(formTwoData);
            cache.setGenInfData(formTwoData.genInfData);
        });
    }

    public LiveData<Boolean> uploadFormTwoById(final long formTwoId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        List<Long> longLoadings = cache.getFormTwoLoading().getValue();
        longLoadings.add(formTwoId);
        cache.setFormTwoLoading(longLoadings);

        appExecutors.diskIO().execute(() -> {
            FormTwoComplete formTwoComplete = formTwoDao.loadFormTwoById(formTwoId);

            if (formTwoComplete == null || formTwoComplete.formTwoData.formTwoApiId != -1) {
                result.postValue(false);
                longLoadings.remove(formTwoId);
                return;
            }

            FormTwoRemote formTwoRemote = Utilities.completeEntityToRemote(formTwoComplete);

            Timber.tag(Tagger.DUMPER).d("FormTwoRepository.uploadFormTwoById2: %s", formTwoRemote);

            galdosService.postFormTwo(formTwoRemote).enqueue(new Callback<ApiResponse<DataResponse>>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<ApiResponse<DataResponse>> call, Response<ApiResponse<DataResponse>> response) {
                    appExecutors.diskIO().execute(() -> {
                        if (response.body() != null && response.body().getData() != null && response.body().getData().getFORM2ACABID() != null) {
                            Integer apiId = response.body().getData().getFORM2ACABID();

                            FormTwoData formTwoData = formTwoComplete.formTwoData;
                            formTwoData.formTwoApiId = apiId;
                            formTwoDao.updateFormTwo(formTwoData);
                            result.postValue(true);
                        } else {
                            result.postValue(false);
                        }

                        longLoadings.remove(formTwoId);
                        cache.setFormTwoLoading(longLoadings);
                    });
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<ApiResponse<DataResponse>> call, Throwable t) {
                    result.postValue(false);

                    longLoadings.remove(formTwoId);
                    cache.setFormTwoLoading(longLoadings);
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

            Timber.tag(Tagger.DUMPER).d("FormTwoRepository.saveForm: %s", formTwoData);

            if (formTwoData.id == 0) {
                formTwoDao.insertFormTwoComplete(formTwoData);
            } else {
                formTwoDao.updateFormTwoComplete(formTwoData);
            }
        });
    }

    public void clearForm() {
        cache.setFormTwoData(null);
    }
}
