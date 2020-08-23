package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.db.FormTwoDao;
import tech.ankainn.edanapplication.model.api.ApiFormTwo;
import tech.ankainn.edanapplication.model.api.DataResponse;
import tech.ankainn.edanapplication.model.dto.FormTwoEntity;
import tech.ankainn.edanapplication.model.dto.FormTwoCompleteData;
import tech.ankainn.edanapplication.model.dto.LivelihoodEntity;
import tech.ankainn.edanapplication.model.dto.MemberEntity;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.retrofit.ApiResponse;
import tech.ankainn.edanapplication.model.factory.FormTwoFactory;
import tech.ankainn.edanapplication.retrofit.Service;
import timber.log.Timber;

public class FormTwoRepository {

    private static final LatLng defaultLatLng = new LatLng(-7.146,-75.009);

    private static FormTwoRepository instance;

    private AppExecutors appExecutors;
    private Service apiService;
    private FormTwoDao formTwoDao;
    private Cache cache;

    public static FormTwoRepository getInstance(AppExecutors appExecutors,
                                                Service apiService,
                                                EdanDatabase edanDatabase,
                                                Cache cache) {
        if(instance == null) {
            synchronized (FormTwoRepository.class) {
                if(instance == null) {
                    instance = new FormTwoRepository(appExecutors, apiService, edanDatabase, cache);
                }
            }
        }
        return instance;
    }

    private FormTwoRepository(AppExecutors appExecutors, Service apiService, EdanDatabase edanDatabase, Cache cache) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
        this.cache = cache;
        formTwoDao = edanDatabase.formTwoDao();
    }

    public LiveData<List<FormTwoData>> getAllFormsFromDb() {
        LiveData<List<FormTwoCompleteData>> source = formTwoDao.getAllFormTwo();
        return Transformations.map(source, FormTwoFactory::fromDbList);
    }

    public LiveData<FormTwoData> getCurrentFormTwoData() {
        return cache.getFormTwoData();
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

        MapLocationData mapLocationData = cache.getMapLocationData().getValue();
        if (mapLocationData != null) {
            formTwoData.mapLocationData = mapLocationData;
        } else {
            formTwoData.mapLocationData.latitude = defaultLatLng.latitude;
            formTwoData.mapLocationData.longitude = defaultLatLng.longitude;
            cache.setMapLocationData(formTwoData.mapLocationData);
        }

        GenInfData genInfData = cache.getGenInfData().getValue();
        if (genInfData != null) {
            formTwoData.genInfData = genInfData;
        } else {
            cache.setGenInfData(formTwoData.genInfData);
        }

        cache.setFormTwoData(formTwoData);
    }

    private void loadFormTwoDataById(long id) {
        appExecutors.diskIO().execute(() -> {
            FormTwoCompleteData source = formTwoDao.loadFormTwoById(id);

            FormTwoData formTwoData = FormTwoFactory.entityToData(source);
            cache.setFormTwoData(formTwoData);
            cache.setGenInfData(formTwoData.genInfData);
            cache.setMapLocationData(formTwoData.mapLocationData);
        });
    }

    public void saveForm() {
        FormTwoData formTwoData = cache.getFormTwoData().getValue();
        if (formTwoData == null) {
            return;
        }

        appExecutors.diskIO().execute(() -> {
            formTwoData.dataVersion++;

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

    public LiveData<Integer> postFormTwo(FormTwoData formTwoData) {
        ApiFormTwo apiFormTwo = FormTwoFactory.apiFromData(formTwoData);
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        apiService.postForm2a(apiFormTwo).enqueue(new Callback<ApiResponse<DataResponse>>() {
            @Override
            public void onResponse(@NotNull Call<ApiResponse<DataResponse>> call,
                                   @NotNull Response<ApiResponse<DataResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    liveData.setValue(response.body().getData().getFORM2ACABID());
                } else {
                    liveData.setValue(-1);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ApiResponse<DataResponse>> call,
                                  @NotNull Throwable t) {
                Timber.e(t);
                liveData.setValue(-1);
            }
        });
        return liveData;
    }

    public void clearForm() {
        cache.setFormTwoData(null);
    }
}
