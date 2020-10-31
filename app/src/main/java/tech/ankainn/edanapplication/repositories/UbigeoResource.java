package tech.ankainn.edanapplication.repositories;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.api.GaldosService;
import tech.ankainn.edanapplication.db.UbigeoDao;
import tech.ankainn.edanapplication.model.api.ResponseWrapper;
import tech.ankainn.edanapplication.model.app.ubigeo.UbigeoLocation;

public class UbigeoResource {

    private final AppExecutors appExecutors;
    private final GaldosService galdosService;
    private final UbigeoDao ubigeoDao;
    private final String ownerCode;

    private final MediatorLiveData<List<UbigeoLocation>> result = new MediatorLiveData<>();

    @MainThread
    UbigeoResource(AppExecutors appExecutors, GaldosService galdosService, UbigeoDao ubigeoDao, String ownerCode) {
        this.appExecutors = appExecutors;
        this.galdosService = galdosService;
        this.ubigeoDao = ubigeoDao;
        this.ownerCode = ownerCode;

        appExecutors.diskIO().execute(() -> {
            List<UbigeoLocation> dbData = ubigeoDao.loadUbigeosByOwnerCode(ownerCode);
            if (dbData == null || dbData.isEmpty()) {
                fetchFromNetwork();
            } else {
                appExecutors.mainThread().execute(() -> result.setValue(dbData));
            }
        });
    }

    @WorkerThread
    private void fetchFromNetwork() {
        Call<ResponseWrapper<List<UbigeoLocation>>> apiResponse = createCall();
        MutableLiveData<List<UbigeoLocation>> apiLiveData = new MutableLiveData<>();
        appExecutors.networkIO().execute(() -> apiResponse.enqueue(new Callback<ResponseWrapper<List<UbigeoLocation>>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseWrapper<List<UbigeoLocation>>> call,
                                   Response<ResponseWrapper<List<UbigeoLocation>>> response) {
                ResponseWrapper<List<UbigeoLocation>> responseWrapper = response.body();
                if (response.isSuccessful() && responseWrapper != null && responseWrapper.data != null && !responseWrapper.data.isEmpty()) {
                    apiLiveData.postValue(responseWrapper.data);
                } else {
                    apiLiveData.postValue(null);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseWrapper<List<UbigeoLocation>>> call, Throwable t) {
                apiLiveData.postValue(null);
            }
        }));

        appExecutors.mainThread().execute(() -> result.addSource(apiLiveData, list -> {
            if (list != null && !list.isEmpty()) {
                for (UbigeoLocation ubigeoLocation : list) {
                    ubigeoLocation.ownerCode = ownerCode;
                }
                appExecutors.diskIO().execute(() -> ubigeoDao.insertAllUbigeos(list));
            }
           result.setValue(list);
        }));
    }

    @NonNull
    @WorkerThread
    private Call<ResponseWrapper<List<UbigeoLocation>>> createCall() {
        int length = ownerCode.length();
        if (length == 1) {
            return galdosService.getDptos();
        } else if (length == 2) {
            return galdosService.getProvinces(ownerCode);
        } else/* if (length == 4) */{
            String dptoCode = ownerCode.substring(0, 2);
            return galdosService.getDistritos(dptoCode, ownerCode);
        }
    }

    public LiveData<List<UbigeoLocation>> asLiveData() {
        return result;
    }
}
