package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.api.ReniecService;
import tech.ankainn.edanapplication.model.api.ReniecData;

public class ReniecRepository {

    private static ReniecRepository instance;

    private AppExecutors appExecutors;
    private ReniecService reniecService;

    public static ReniecRepository getInstance(AppExecutors appExecutors, ReniecService reniecService) {
        if(instance == null) {
            synchronized (ReniecRepository.class) {
                if(instance == null) {
                    instance = new ReniecRepository(appExecutors, reniecService);
                }
            }
        }
        return instance;
    }

    private ReniecRepository(AppExecutors appExecutors, ReniecService reniecService) {
        this.appExecutors = appExecutors;
        this.reniecService = reniecService;
    }

    public LiveData<ReniecData> searchPersonData(String dni) {
        MutableLiveData<ReniecData> liveData = new MutableLiveData<>();
        appExecutors.networkIO().execute(() -> {
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
        });
        return liveData;
    }
}
