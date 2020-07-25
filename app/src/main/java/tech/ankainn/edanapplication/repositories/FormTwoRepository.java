package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.model.apiFormTwo.ApiFormTwo;
import tech.ankainn.edanapplication.model.apiFormTwo.DataResponse;
import tech.ankainn.edanapplication.model.dto.FormTwoWithMembers;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.retrofit.ApiListResponse;
import tech.ankainn.edanapplication.retrofit.ApiResponse;
import tech.ankainn.edanapplication.retrofit.ApiService;
import tech.ankainn.edanapplication.util.FormTwoFactory;
import timber.log.Timber;

public class FormTwoRepository {

    private static FormTwoRepository instance;

    private FormTwoData currentData;
    private MutableLiveData<FormTwoData> newForm = new MutableLiveData<>();
    private MediatorLiveData<List<FormTwoData>> listForms = new MediatorLiveData<>();

    private AppExecutors appExecutors;
    private ApiService apiService;
    private EdanDatabase edanDatabase;

    private FormTwoRepository(AppExecutors appExecutors, ApiService apiService, EdanDatabase edanDatabase) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
        this.edanDatabase = edanDatabase;

        listForms.addSource(newForm, formTwoData -> {
            List<FormTwoData> list = listForms.getValue();
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(formTwoData);
            listForms.setValue(list);
        });
    }

    public static FormTwoRepository getInstance(AppExecutors appExecutors, ApiService apiService, EdanDatabase edanDatabase) {
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
        LiveData<List<FormTwoWithMembers>> source = edanDatabase.formTwoDao().getAllFormTwoWithMembers();
        return Transformations.map(source, FormTwoFactory::fromDbList);
    }

    public LiveData<Integer> postFormTwo(FormTwoData formTwoData) {
        ApiFormTwo apiFormTwo = FormTwoFactory.apiFromData(formTwoData);
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        apiService.postForm2a(apiFormTwo).enqueue(new Callback<ApiResponse<DataResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<DataResponse>> call, Response<ApiResponse<DataResponse>> response) {
                Timber.v("onResponse: %s", response);
                if(response.isSuccessful()) {
                    liveData.setValue(response.body().getData().getFORM2ACABID());
                } else {
                    liveData.setValue(-1);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<DataResponse>> call, Throwable t) {
                Timber.e(t);
                liveData.setValue(-1);
            }
        });
        return liveData;
    }

    public void saveForm(FormTwoData formTwoData) {
        newForm.setValue(formTwoData);
    }

    public LiveData<List<FormTwoData>> getListFormTwo() {
        return listForms;
    }

    public LiveData<List<ApiListResponse.Datum>> getListDatum() {
        MutableLiveData<List<ApiListResponse.Datum>> liveData = new MutableLiveData<>();
        apiService.getList().enqueue(new Callback<ApiListResponse>() {
            @Override
            public void onResponse(Call<ApiListResponse> call, Response<ApiListResponse> response) {
                if(response.isSuccessful()) {
                    liveData.setValue(response.body().getData());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiListResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    public void setCurrentData(FormTwoData data) {
        currentData = data;
    }

    public FormTwoData getCurrentData() {
        return currentData;
    }

    public boolean hasCurrentData() {
        return currentData != null;
    }
}
