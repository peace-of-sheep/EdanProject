package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

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
import tech.ankainn.edanapplication.model.dto.FormTwoWithMembers;
import tech.ankainn.edanapplication.model.dto.MemberEntity;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.retrofit.ApiResponse;
import tech.ankainn.edanapplication.retrofit.ApiService;
import tech.ankainn.edanapplication.model.factory.FormTwoFactory;
import tech.ankainn.edanapplication.util.Tuple2;
import timber.log.Timber;

import static tech.ankainn.edanapplication.repositories.FormTwoRepository.State.*;

public class FormTwoRepository {

    private static FormTwoRepository instance;

    private AppExecutors appExecutors;
    private ApiService apiService;
    private EdanDatabase edanDatabase;

    private FormTwoDao formTwoDao;

    private MutableLiveData<Tuple2<State, FormTwoData>> currentData;

    private FormTwoRepository(AppExecutors appExecutors, ApiService apiService, EdanDatabase edanDatabase) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
        this.edanDatabase = edanDatabase;

        formTwoDao = edanDatabase.formTwoDao();

        currentData = new MutableLiveData<>();
    }

    public static FormTwoRepository getInstance(AppExecutors appExecutors,
                                                ApiService apiService,
                                                EdanDatabase edanDatabase) {
        if(instance == null) {
            synchronized (FormTwoRepository.class) {
                if(instance == null) {
                    instance = new FormTwoRepository(appExecutors, apiService, edanDatabase);
                }
            }
        }
        return instance;
    }

    public LiveData<List<FormTwoData>> getAllFormsFromDb() {
        LiveData<List<FormTwoWithMembers>> source = formTwoDao.getAllFormTwoWithMembers();
        return Transformations.map(source, FormTwoFactory::fromDbList);
    }

    public LiveData<FormTwoData> getCurrentFormTwoData() {
        return Transformations.map(currentData, input -> input.second);
    }

    public void createFormTwoData() {
        FormTwoData formTwoData = FormTwoFactory.createEmptyFormTwoData();
        currentData.setValue(new Tuple2<>(CREATION, formTwoData));
    }

    public void updateFormTwoData(FormTwoData formTwoData) {
        FormTwoData result = FormTwoFactory.cloneFormTwoData(formTwoData);
        currentData.setValue(new Tuple2<>(UPDATE, result));
    }

    public void saveForm() {
        Tuple2<State, FormTwoData> tuple = currentData.getValue();
        if (tuple == null) return;

        FormTwoData formTwoData = tuple.second;

        formTwoData.dataVersion++;

        FormTwoEntity formTwoEntity = FormTwoFactory.dataToEntity(formTwoData);
        List<MemberData> listMember = formTwoData.listMemberData;

        List<MemberEntity> listResult = new ArrayList<>();
        if (listMember != null && listMember.size() > 0) {
            for (MemberData memberData : listMember) {
                MemberEntity memberEntity = FormTwoFactory.dataToEntity(memberData);
                listResult.add(memberEntity);
            }
        }

        if (tuple.first == CREATION) {
            appExecutors.diskIO().execute(() -> {
                if (listResult.size() > 0) {
                    formTwoDao.insertFormTwoWithMember(formTwoEntity, listResult);
                } else {
                    formTwoDao.insertFormTwo(formTwoEntity);
                }
            });
        } else if (tuple.first == UPDATE) {
            appExecutors.diskIO().execute(() -> {
                if (listResult.size() > 0) {
                    formTwoDao.updateFormTwoWithMember(formTwoEntity, listResult);
                } else {
                    formTwoDao.updateFormTwo(formTwoEntity);
                }
            });
        }

        currentData = new MutableLiveData<>();
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

    enum State {
        CREATION, UPDATE
    }

    // TODO test
    public FormTwoData getCurrent() {
        if (currentData.getValue() == null) {
            return null;
        }
        return currentData.getValue().second;
    }

    public static FormTwoRepository getInstance() {
        return instance;
    }
}
