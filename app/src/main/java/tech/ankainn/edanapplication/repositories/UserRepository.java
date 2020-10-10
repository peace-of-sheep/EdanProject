package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.api.GaldosService;
import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.db.UserDao;
import tech.ankainn.edanapplication.model.api.auth.AuthCredentials;
import tech.ankainn.edanapplication.model.api.auth.AuthResponse;
import tech.ankainn.edanapplication.model.app.auth.UserData;
import tech.ankainn.edanapplication.model.factory.ModelFactory;

public class UserRepository {

    private static UserRepository instance;

    private AppExecutors appExecutors;

    private Cache cache;
    private GaldosService galdosService;
    private UserDao userDao;

    private UserRepository(AppExecutors appExecutors, Cache cache, GaldosService galdosService, EdanDatabase edanDatabase) {
        this.appExecutors = appExecutors;
        this.cache = cache;
        this.galdosService = galdosService;
        this.userDao = edanDatabase.userDao();
    }

    public static UserRepository getInstance(AppExecutors appExecutors, Cache cache, GaldosService galdosService, EdanDatabase edanDatabase) {
        if(instance == null) {
            synchronized (UserRepository.class) {
                if(instance == null) {
                    instance = new UserRepository(appExecutors, cache, galdosService, edanDatabase);
                }
            }
        }
        return instance;
    }

    public LiveData<Boolean> loadUser(AuthCredentials authCredentials) {
        final MutableLiveData<AuthResponse> networkSearch = new MutableLiveData<>();
        galdosService.postLogin(authCredentials).enqueue(new Callback<AuthResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                AuthResponse authResponse = response.body();

                if (authResponse != null && !authResponse.getApiUser().isEmpty()) {
                    networkSearch.postValue(authResponse);
                } else {
                    networkSearch.postValue(null);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                networkSearch.postValue(null);
            }
        });

        MediatorLiveData<Boolean> diskSearch = new MediatorLiveData<>();
        diskSearch.addSource(networkSearch, authResponse -> appExecutors.diskIO().execute(() -> {

            //TODO redo this hash xd
            String hash = authCredentials.getUsername()+authCredentials.getClave();

            UserData userData;

            if (authResponse == null) {
                userData = userDao.loadUserDataByHash(hash);
            } else {
                userData = ModelFactory.userFromRemote(authResponse, hash);
                userDao.insertUser(userData);
            }

            if (userData != null) {
                saveUser(userData);
            }

            diskSearch.postValue(userData != null);
        }));

        return diskSearch;
    }

    public LiveData<UserData> getCurrentUser() {
        return cache.getUserData();
    }

    private void saveUser(UserData userData) {
        cache.setUserData(userData);
    }
}
