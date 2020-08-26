package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.model.AuthCredentials;
import tech.ankainn.edanapplication.api.RestResponse;
import tech.ankainn.edanapplication.api.EdanApiService;
import timber.log.Timber;

public class UserRepository {

    private static UserRepository instance;

    private AppExecutors appExecutors;
    private EdanApiService apiService;

    private UserRepository(AppExecutors appExecutors, EdanApiService apiService) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
    }

    public static UserRepository getInstance(AppExecutors appExecutors, EdanApiService apiService) {
        if(instance == null) {
            synchronized (UserRepository.class) {
                if(instance == null) {
                    instance = new UserRepository(appExecutors, apiService);
                }
            }
        }
        return instance;
    }

    public LiveData<String> loadUser(AuthCredentials authCredentials) {
        final MutableLiveData<String> authToken = new MutableLiveData<>();
        appExecutors.networkIO().execute(() ->
                apiService.postSignInAuth(authCredentials).enqueue(new Callback<RestResponse<String>>() {
                    @Override
                    public void onResponse(Call<RestResponse<String>> call, Response<RestResponse<String>> response) {
                        Timber.d("onResponse: %s", response);
                        if(response.code() >= 200 && response.code() < 300) {
                            String token = response.body().getData();
                            authToken.postValue(token);
                        }
                    }

                    @Override
                    public void onFailure(Call<RestResponse<String>> call, Throwable t) {
                        Timber.e(t, "error in loading");
                    }
        }));
        return authToken;
    }
}
