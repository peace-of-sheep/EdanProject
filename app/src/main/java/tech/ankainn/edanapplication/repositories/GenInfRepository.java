package tech.ankainn.edanapplication.repositories;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.google.android.gms.location.FusedLocationProviderClient;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.model.factory.GenInfFactory;
import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;

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

    public LiveData<MapLocationData> getMapLocationData() {
        return cache.getMapLocationData();
    }

    public LiveData<GenInfData> getGenInfData() {
        return cache.getGenInfData();
    }

    @RequiresPermission("android.permission.ACCESS_COARSE_LOCATION")
    public void getLastLocation() throws SecurityException {
        locationProviderClient.getLastLocation()
                .addOnCompleteListener(appExecutors.diskIO(), locationTask -> {
                    if (locationTask.isSuccessful() && locationTask.getResult() != null) {
                        updateLocation(locationTask.getResult());
                    }
                });
    }

    private void updateLocation(Location location) {
        MapLocationData originalData = cache.getMapLocationData().getValue();
        if (originalData == null) return;

        originalData.latitude = location.getLatitude();
        originalData.longitude = location.getLongitude();

        if (location.hasAltitude()) {
            originalData.altitude = (int) location.getAltitude();
        }

        cache.setMapLocationData(originalData);
    }
}
