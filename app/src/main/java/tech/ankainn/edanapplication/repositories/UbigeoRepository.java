package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.api.GaldosService;
import tech.ankainn.edanapplication.db.UbigeoDao;
import tech.ankainn.edanapplication.model.api.ResponseWrapper;
import tech.ankainn.edanapplication.model.app.auth.UserData;
import tech.ankainn.edanapplication.model.app.ubigeo.DeptEntity;
import tech.ankainn.edanapplication.model.app.ubigeo.DistEntity;
import tech.ankainn.edanapplication.model.app.ubigeo.LocalEntity;
import tech.ankainn.edanapplication.model.app.ubigeo.ProvEntity;
import tech.ankainn.edanapplication.model.app.ubigeo.UbigeoLocation;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class UbigeoRepository {

    private static UbigeoRepository instance;

    private AppExecutors appExecutors;
    private GaldosService galdosService;
    private UbigeoDao ubigeoDao;
    private Cache cache;

    public static UbigeoRepository getInstance(AppExecutors appExecutors, GaldosService galdosService, UbigeoDao ubigeoDao, Cache cache) {
        if (instance == null) {
            synchronized (UbigeoRepository.class) {
                if (instance == null) {
                    instance = new UbigeoRepository(appExecutors, galdosService, ubigeoDao, cache);
                }
            }
        }
        return instance;
    }

    private UbigeoRepository(AppExecutors appExecutors, GaldosService galdosService, UbigeoDao ubigeoDao, Cache cache) {
        this.appExecutors = appExecutors;
        this.galdosService = galdosService;
        this.ubigeoDao = ubigeoDao;
        this.cache = cache;
    }

    public LiveData<Boolean> loadUbigeos(String ubigeo) {
        galdosService.getUbigeos(ubigeo).enqueue(new Callback<ResponseWrapper<List<DeptEntity>>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseWrapper<List<DeptEntity>>> call, Response<ResponseWrapper<List<DeptEntity>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saveUbigeos(response.body().data);
                } else {
                    Timber.tag(Tagger.DUMPER).d("UbigeoRepository.onResponse: check code and body, code: %d", response.code());
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseWrapper<List<DeptEntity>>> call, Throwable t) {
                Timber.tag(Tagger.WARN).e(t, "UbigeoRepository.onFailure: loadUbigeos failed");
            }
        });

        return new MutableLiveData<>(true);
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

        Timber.tag(Tagger.DUMPER).d("UbigeoRepository.loadDistList: provCode %s ubigeo %s", provCode, ubigeo);

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

    private void saveUbigeos(List<DeptEntity> data) {
        appExecutors.diskIO().execute(() -> {
            for (DeptEntity deptEntity : data) {
                saveDept(deptEntity);
            }
        });
    }

    private void saveDept(DeptEntity deptEntity) {
        UbigeoLocation ubigeoLocation = new UbigeoLocation();

        ubigeoLocation.code = deptEntity.code;
        ubigeoLocation.name = deptEntity.name;
        ubigeoLocation.ownerCode = "0";

        ubigeoDao.insertDept(ubigeoLocation);

        for (ProvEntity provEntity : deptEntity.prov) {
            saveProv(provEntity, deptEntity.code);
        }
    }

    private void saveProv(ProvEntity provEntity, String ownerCode) {
        UbigeoLocation ubigeoLocation = new UbigeoLocation();

        ubigeoLocation.code = provEntity.code;
        ubigeoLocation.name = provEntity.name;
        ubigeoLocation.ownerCode = ownerCode;

        ubigeoDao.insertProv(ubigeoLocation);

        for (DistEntity distEntity : provEntity.dist) {
            saveDist(distEntity, provEntity.code);
        }
    }

    private void saveDist(DistEntity distEntity, String ownerCode) {
        UbigeoLocation ubigeoLocation = new UbigeoLocation();

        ubigeoLocation.code = distEntity.code;
        ubigeoLocation.name = distEntity.name;
        ubigeoLocation.ownerCode = ownerCode;

        ubigeoDao.insertDist(ubigeoLocation);

        for (LocalEntity localEntity : distEntity.local) {
            saveLocal(localEntity, distEntity.code);
        }
    }

    private void saveLocal(LocalEntity localEntity, String ownerCode) {
        UbigeoLocation ubigeoLocation = new UbigeoLocation();

        ubigeoLocation.code = localEntity.code;
        ubigeoLocation.name = localEntity.name;
        ubigeoLocation.ownerCode = ownerCode;

        ubigeoDao.insertLocal(ubigeoLocation);
    }

    private String retrieveUbigeo(Cache cache) {
        UserData userData = cache.getUserData().getValue();
        return userData == null ? "" : userData.ubigeo;
    }
}
