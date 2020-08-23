package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;

import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;

public class GenInfRepository {

    private static GenInfRepository instance;

    private Cache cache;

    public static GenInfRepository getInstance(Cache cache) {
        if (instance == null) {
            instance = new GenInfRepository(cache);
        }
        return instance;
    }

    private GenInfRepository(Cache cache) {
        this.cache = cache;
    }

    public LiveData<MapLocationData> getMapLocationData() {
        return cache.getMapLocationData();
    }

    public LiveData<GenInfData> getGenInfData() {
        return cache.getGenInfData();
    }
}
