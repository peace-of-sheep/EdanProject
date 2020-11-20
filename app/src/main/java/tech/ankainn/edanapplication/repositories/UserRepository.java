package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
import tech.ankainn.edanapplication.util.Tagger;
import tech.ankainn.edanapplication.util.Utilities;
import timber.log.Timber;

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

    public LiveData<UserData> loadUser(AuthCredentials authCredentials) {
        final MutableLiveData<AuthResponse> networkSearch = new MutableLiveData<>();
        galdosService.postLogin(authCredentials).enqueue(new Callback<AuthResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                if (response.isSuccessful())
                    networkSearch.postValue(response.body());
                else
                    networkSearch.postValue(null);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                networkSearch.postValue(null);
            }
        });

        MediatorLiveData<UserData> diskSearch = new MediatorLiveData<>();
        diskSearch.addSource(networkSearch, authResponse -> appExecutors.diskIO().execute(() -> {

            String hash = createHash(authCredentials);

            UserData localUser = userDao.loadUserDataByHash(hash);

            if (authResponse == null) {
                if (localUser == null) {
                    diskSearch.postValue(null);
                } else {
                    saveUser(localUser);
                    diskSearch.postValue(localUser);
                }
            } else {
                UserData remoteUser = Utilities.userFromRemote(authResponse, hash);

                if (localUser == null) {
                    remoteUser.id = userDao.insertUser(remoteUser);
                } else {
                    remoteUser.id = localUser.id;
                    userDao.updateUser(remoteUser);
                }

                saveUser(remoteUser);
                diskSearch.postValue(remoteUser);
            }
        }));

        return diskSearch;
    }

    public LiveData<UserData> getCurrentUser() {
        return cache.getUserData();
    }

    private void saveUser(UserData userData) {

        cache.setUserData(userData);
    }

    private String createHash(AuthCredentials authCredentials) {
        String securePass = getSecurePassword(authCredentials.getClave());
        return authCredentials.getUsername()+securePass;
    }

    private String getSecurePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            //md.update(salt);
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            Timber.e(e);
        }
        return generatedPassword;
    }

    public LiveData<Long> getUserId() {
        LiveData<UserData> source = cache.getUserData();
        return Transformations.map(source, user -> user != null ? user.id : -1L);
    }

    public long getRawUserId() {
        UserData userData = cache.getUserData().getValue();
        return userData != null ? userData.id : -1L;
    }

    public void logout() {
        clearAllCache();
    }

    private void clearAllCache() {
        cache.setUserData(null);

        cache.setGenInfData(null);

        cache.setFormTwoData(null);
        cache.setFormTwoLoading(null);

        cache.setFormOneData(null);
        cache.setFormOneLoading(null);
    }

    public String getRawUsername() {
        UserData userData = cache.getUserData().getValue();
        return userData != null ? userData.username : "";
    }
}
