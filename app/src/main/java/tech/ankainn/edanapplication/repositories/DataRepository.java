package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.api.GaldosService;
import tech.ankainn.edanapplication.db.DataCodesDao;
import tech.ankainn.edanapplication.db.UbigeoDao;
import tech.ankainn.edanapplication.model.api.ResponseWrapper;
import tech.ankainn.edanapplication.model.api.livelihood.LivelihoodRemote;
import tech.ankainn.edanapplication.model.api.livelihood.LivelihoodTypeRemote;
import tech.ankainn.edanapplication.model.api.ubigeo.DeptEntity;
import tech.ankainn.edanapplication.model.api.ubigeo.DistEntity;
import tech.ankainn.edanapplication.model.api.ubigeo.LocalEntity;
import tech.ankainn.edanapplication.model.api.ubigeo.ProvEntity;
import tech.ankainn.edanapplication.model.app.master.DataEntity;
import tech.ankainn.edanapplication.model.app.master.MasterTypes;
import tech.ankainn.edanapplication.model.app.ubigeo.UbigeoLocation;
import tech.ankainn.edanapplication.util.CombinedLiveData;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class DataRepository {

    private static DataRepository instance;

    private AppExecutors appExecutors;
    private GaldosService galdosService;
    private UbigeoDao ubigeoDao;
    private DataCodesDao dataCodesDao;

    public static DataRepository getInstance(AppExecutors appExecutors, GaldosService galdosService, UbigeoDao ubigeoDao, DataCodesDao dataCodesDao) {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository(appExecutors, galdosService, ubigeoDao, dataCodesDao);
                }
            }
        }
        return instance;
    }

    private DataRepository(AppExecutors appExecutors, GaldosService galdosService, UbigeoDao ubigeoDao, DataCodesDao dataCodesDao) {
        this.appExecutors = appExecutors;
        this.galdosService = galdosService;
        this.ubigeoDao = ubigeoDao;
        this.dataCodesDao = dataCodesDao;
    }

    public LiveData<Boolean> loadData(String ubigeo) {
        LiveData<Boolean> ubigeosSource = loadUbigeos(ubigeo);
        LiveData<Boolean> dataSource = loadDataCodes();
        return new CombinedLiveData<>(ubigeosSource, dataSource, (first, second) -> {
            if (first != null && second != null)
                return true;
            return null;
        });
    }

    private LiveData<Boolean> loadDataCodes() {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        galdosService.getLivelihoodsCodes().enqueue(new Callback<ResponseWrapper<List<LivelihoodRemote>>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseWrapper<List<LivelihoodRemote>>> call, Response<ResponseWrapper<List<LivelihoodRemote>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saveLivelihoodsCodes(response.body().data);
                }
                result.postValue(true);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseWrapper<List<LivelihoodRemote>>> call, Throwable t) {
                Timber.tag(Tagger.WARN).e(t, "DataRepository.onFailure: loadDataCodes failed");
                result.postValue(true);
            }
        });
        return result;
    }

    private void saveLivelihoodsCodes(List<LivelihoodRemote> data) {
        List<DataEntity> dataEntities = new ArrayList<>();
        for (LivelihoodRemote livelihoodRemote : data) {
            DataEntity dataLivelihood = new DataEntity();
            dataLivelihood.code = livelihoodRemote.code;
            dataLivelihood.name = livelihoodRemote.name;
            dataLivelihood.type = MasterTypes.LIVELIHOODS;

            dataEntities.add(dataLivelihood);

            for (LivelihoodTypeRemote livelihoodTypeRemote : livelihoodRemote.type) {
                DataEntity dataTypeLivelihood = new DataEntity();
                dataTypeLivelihood.code = livelihoodTypeRemote.code;
                dataTypeLivelihood.name = livelihoodTypeRemote.name;
                dataTypeLivelihood.ownerCode = livelihoodRemote.code;
                dataTypeLivelihood.type = MasterTypes.LIVELIHOOD_TYPES;

                dataEntities.add(dataTypeLivelihood);
            }
        }

        appExecutors.diskIO().execute(() -> {
            DataEntity[] toInsert = new DataEntity[dataEntities.size()];
            dataCodesDao.insertDataCodes(dataEntities.toArray(toInsert));
        });
    }

    private LiveData<Boolean> loadUbigeos(String ubigeo) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        galdosService.getUbigeos(ubigeo).enqueue(new Callback<ResponseWrapper<List<DeptEntity>>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseWrapper<List<DeptEntity>>> call, Response<ResponseWrapper<List<DeptEntity>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saveUbigeos(response.body().data);
                }
                result.postValue(true);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseWrapper<List<DeptEntity>>> call, Throwable t) {
                Timber.tag(Tagger.WARN).e(t, "DataRepository.onFailure: loadUbigeos failed");
                result.postValue(true);
            }
        });
        return result;
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
}
