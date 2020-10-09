package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.api.GaldosService;
import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.db.UserDao;
import tech.ankainn.edanapplication.model.api.auth.ApiUser;
import tech.ankainn.edanapplication.model.api.auth.AuthCredentials;
import tech.ankainn.edanapplication.model.api.auth.AuthResponse;

public class UserRepository {

    private static UserRepository instance;

    private AppExecutors appExecutors;
    private GaldosService galdosService;
    private UserDao userDao;

    private ApiUser apiUser;
    private String token;

    private UserRepository(AppExecutors appExecutors, GaldosService galdosService, EdanDatabase edanDatabase) {
        this.appExecutors = appExecutors;
        this.galdosService = galdosService;
        this.userDao = edanDatabase.userDao();
    }

    public static UserRepository getInstance(AppExecutors appExecutors, GaldosService galdosService, EdanDatabase edanDatabase) {
        if(instance == null) {
            synchronized (UserRepository.class) {
                if(instance == null) {
                    instance = new UserRepository(appExecutors, galdosService, edanDatabase);
                }
            }
        }
        return instance;
    }

    public LiveData<Boolean> loadUser(AuthCredentials authCredentials) {
        final MutableLiveData<Boolean> authToken = new MutableLiveData<>();
        galdosService.postLogin(authCredentials).enqueue(new Callback<AuthResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                if (response.body() == null) {
                    authToken.postValue(false);
                    return;
                }

                List<ApiUser> list = response.body().getApiUser();
                if (list.isEmpty()) {
                    authToken.postValue(false);
                } else {
                    saveUser(response.body());
                    authToken.postValue(true);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                authToken.postValue(false);
            }
        });
        return authToken;
    }

    public ApiUser getCurrentUser() {
        return apiUser;
    }

    private void saveUser(AuthResponse authResponse) {
        this.token = authResponse.getToken();
        this.apiUser = authResponse.getApiUser().get(0);
    }
}
