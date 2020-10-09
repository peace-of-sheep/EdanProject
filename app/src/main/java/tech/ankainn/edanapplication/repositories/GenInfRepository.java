package tech.ankainn.edanapplication.repositories;

import android.location.Location;
import android.nfc.Tag;

import androidx.annotation.RequiresPermission;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.model.app.geninf.ExtraData;
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;
import tech.ankainn.edanapplication.model.app.geninf.HeaderData;
import tech.ankainn.edanapplication.model.app.geninf.MapLocationData;
import tech.ankainn.edanapplication.util.Tagger;
import tech.ankainn.edanapplication.util.Tuple2;
import timber.log.Timber;

public class GenInfRepository {

    private static GenInfRepository instance;

    private AppExecutors appExecutors;
    private FusedLocationProviderClient locationProviderClient;
    private Cache cache;

    public static GenInfRepository getInstance(AppExecutors appExecutors,
                                               FusedLocationProviderClient client,
                                               Cache cache) {
        if (instance == null) {
            instance = new GenInfRepository(appExecutors, client, cache);
        }
        return instance;
    }

    private GenInfRepository(AppExecutors appExecutors,
                             FusedLocationProviderClient client,
                             Cache cache) {
        this.appExecutors = appExecutors;
        this.locationProviderClient = client;
        this.cache = cache;
    }

    public LiveData<HeaderData> loadHeaderData() {
        LiveData<GenInfData> source = cache.getGenInfData();
        MediatorLiveData<HeaderData> result = new MediatorLiveData<>();
        result.addSource(source, genInfData -> {
            if (genInfData != null)
                result.setValue(genInfData.headerData);
        });
        return result;
    }

    public LiveData<ExtraData> loadExtraData() {
        LiveData<GenInfData> source = cache.getGenInfData();
        MediatorLiveData<ExtraData> result = new MediatorLiveData<>();
        result.addSource(source, genInfData -> {
            if (genInfData != null) {
                result.setValue(genInfData.extraData);
            }
        });
        return result;
    }

    public LiveData<MapLocationData>  loadMapLocationData() {
        LiveData<GenInfData> source = cache.getGenInfData();
        MediatorLiveData<MapLocationData> result = new MediatorLiveData<>();
        result.addSource(source, genInfData -> {
            if (genInfData != null) {
                result.setValue(genInfData.mapLocationData);
            }
        });
        return result;
    }

    @RequiresPermission("android.permission.ACCESS_COARSE_LOCATION")
    public LiveData<Tuple2<Double, Double>> loadLastLocation() throws SecurityException {
        MutableLiveData<Tuple2<Double, Double>> latLng = new MutableLiveData<>();
        locationProviderClient.getLastLocation()
                .addOnCompleteListener(appExecutors.networkIO(), locationTask -> {
                    Timber.tag(Tagger.DUMPER).d("loadLastLocation: %s", locationTask.getResult());
                    if (locationTask.isSuccessful() && locationTask.getResult() != null) {
                        Location location = locationTask.getResult();
                        latLng.postValue(new Tuple2<>(location.getLatitude(), location.getLongitude()));
                    } else {
                        latLng.postValue(null);
                    }
                });
        return latLng;
    }
}
