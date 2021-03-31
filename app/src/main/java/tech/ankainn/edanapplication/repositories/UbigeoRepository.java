package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.List;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.db.UbigeoDao;
import tech.ankainn.edanapplication.model.app.auth.UserData;
import tech.ankainn.edanapplication.model.app.ubigeo.UbigeoLocation;

public class UbigeoRepository {

    private static UbigeoRepository instance;

    private AppExecutors appExecutors;
    private UbigeoDao ubigeoDao;
    private Cache cache;

    public static UbigeoRepository getInstance(AppExecutors appExecutors, UbigeoDao ubigeoDao, Cache cache) {
        if (instance == null) {
            synchronized (UbigeoRepository.class) {
                if (instance == null) {
                    instance = new UbigeoRepository(appExecutors, ubigeoDao, cache);
                }
            }
        }
        return instance;
    }

    private UbigeoRepository(AppExecutors appExecutors, UbigeoDao ubigeoDao, Cache cache) {
        this.appExecutors = appExecutors;
        this.ubigeoDao = ubigeoDao;
        this.cache = cache;
    }

    public LiveData<List<UbigeoLocation>> loadDptosList() {
        String ubigeo = retrieveUbigeo(cache);
        MutableLiveData<List<UbigeoLocation>> liveData = new MutableLiveData<>();

        appExecutors.diskIO().execute(() -> {
            if (ubigeo.isEmpty()) {
                List<UbigeoLocation> list = ubigeoDao.loadUbigeosByOwnerCode("0");
                liveData.postValue(list);
            } else if (ubigeo.length() >= 2) {
                String code = ubigeo.substring(0, 2);
                UbigeoLocation ubigeoLocation = ubigeoDao.loadUbigeoByCode(code);

                liveData.postValue(
                        ubigeoLocation == null ?
                                Collections.emptyList() :
                                Collections.singletonList(ubigeoLocation)
                );
            }
        });

        return liveData;
    }

    public LiveData<List<UbigeoLocation>> loadProvList(String dptoCode) {

        String ubigeo = retrieveUbigeo(cache);
        MutableLiveData<List<UbigeoLocation>> liveData = new MutableLiveData<>();

        appExecutors.diskIO().execute(() -> {
            if (ubigeo.length() == 2) {
                List<UbigeoLocation> list = ubigeoDao.loadUbigeosByOwnerCode(dptoCode);
                liveData.postValue(list);
            } else if (ubigeo.length() >= 4) {
                String code = ubigeo.substring(0, 4);
                UbigeoLocation ubigeoLocation = ubigeoDao.loadUbigeoByCode(code);

                liveData.postValue(
                        ubigeoLocation == null ?
                                Collections.emptyList() :
                                Collections.singletonList(ubigeoLocation)
                );
            }
        });

        return liveData;
    }

    public LiveData<List<UbigeoLocation>> loadDistList(String provCode) {
        String ubigeo = retrieveUbigeo(cache);
        MutableLiveData<List<UbigeoLocation>> liveData = new MutableLiveData<>();

        appExecutors.diskIO().execute(() -> {
            if (ubigeo.length() <= 4) {
                List<UbigeoLocation> list = ubigeoDao.loadUbigeosByOwnerCode(provCode);
                liveData.postValue(list);
            } else if (ubigeo.length() >= 6) {
                String code = ubigeo.substring(0, 6);
                UbigeoLocation ubigeoLocation = ubigeoDao.loadUbigeoByCode(code);

                if (ubigeoLocation == null) {
                    liveData.postValue(null);
                    return;
                }

                liveData.postValue(Collections.singletonList(ubigeoLocation));
            }
        });

        return liveData;
    }

    public LiveData<List<UbigeoLocation>> loadLocalList(String distCode) {
        String ubigeo = retrieveUbigeo(cache);
        MutableLiveData<List<UbigeoLocation>> liveData = new MutableLiveData<>();

        appExecutors.diskIO().execute(() -> {
            if (ubigeo.length() <= 6) {
                List<UbigeoLocation> list = ubigeoDao.loadUbigeosByOwnerCode(distCode);
                liveData.postValue(list);
            } else if (ubigeo.length() >= 10) {
                String code = ubigeo.substring(0, 10);
                UbigeoLocation ubigeoLocation = ubigeoDao.loadUbigeoByCode(code);

                if (ubigeoLocation == null) {
                    liveData.postValue(null);
                    return;
                }

                liveData.postValue(Collections.singletonList(ubigeoLocation));
            }
        });

        return liveData;
    }

    private String retrieveUbigeo(Cache cache) {
        UserData userData = cache.getUserData().getValue();
        return userData == null ? "" : userData.ubigeo;
    }
}
