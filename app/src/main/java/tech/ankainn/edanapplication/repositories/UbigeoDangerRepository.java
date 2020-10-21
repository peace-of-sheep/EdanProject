package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.api.GaldosService;
import tech.ankainn.edanapplication.db.UbigeoDao;
import tech.ankainn.edanapplication.model.app.ubigeo.UbigeoLocation;

public class UbigeoDangerRepository {

    private static UbigeoDangerRepository instance;

    private AppExecutors appExecutors;
    private GaldosService galdosService;
    private UbigeoDao ubigeoDao;

    public static UbigeoDangerRepository getInstance(AppExecutors appExecutors, GaldosService galdosService, UbigeoDao ubigeoDao) {
        if(instance == null) {
            synchronized (UbigeoDangerRepository.class) {
                if(instance == null) {
                    instance = new UbigeoDangerRepository(appExecutors, galdosService, ubigeoDao);
                }
            }
        }
        return instance;
    }

    private UbigeoDangerRepository(AppExecutors appExecutors, GaldosService galdosService, UbigeoDao ubigeoDao) {
        this.appExecutors = appExecutors;
        this.galdosService = galdosService;
        this.ubigeoDao = ubigeoDao;
    }

    public LiveData<List<UbigeoLocation>> loadDptosList() {
        return new UbigeoResource(appExecutors, galdosService, ubigeoDao, "0").asLiveData();
    }

    public LiveData<List<UbigeoLocation>> loadProvList(String dptoCode) {
        return new UbigeoResource(appExecutors, galdosService, ubigeoDao, dptoCode).asLiveData();
    }

    public LiveData<List<UbigeoLocation>> loadDistList(String provCode) {
        return new UbigeoResource(appExecutors, galdosService, ubigeoDao, provCode).asLiveData();
    }
}
