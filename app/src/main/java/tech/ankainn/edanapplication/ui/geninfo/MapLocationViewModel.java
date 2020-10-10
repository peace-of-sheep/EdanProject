package tech.ankainn.edanapplication.ui.geninfo;

import android.annotation.SuppressLint;
import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.model.app.geninf.MapLocationData;
import tech.ankainn.edanapplication.repositories.GenInfRepository;
import tech.ankainn.edanapplication.util.AbsentLiveData;

public class MapLocationViewModel extends ViewModel {

    private MutableLiveData<Boolean> toSearchLocation = new MutableLiveData<>();

    private MediatorLiveData<MapLocationData> mapLocationData = new MediatorLiveData<>();
    private MapLocationData currentData;

    @SuppressLint("MissingPermission")
    public MapLocationViewModel(GenInfRepository genInfRepository) {

        LiveData<MapLocationData> source = genInfRepository.loadMapLocationData();
        mapLocationData.addSource(source, mapLocationData -> {
            this.currentData = mapLocationData;
            this.mapLocationData.setValue(mapLocationData);
        });

        LiveData<Location> fusedLocation = Transformations.switchMap(toSearchLocation, search -> {
            if (search) {
                return genInfRepository.loadLastLocation();
            } else {
                return AbsentLiveData.create();
            }
        });
        mapLocationData.addSource(fusedLocation, location -> {
            if (location != null) {
                currentData.latitude = location.getLatitude();
                currentData.longitude = location.getLongitude();
                if (location.hasAltitude()) {
                    currentData.altitude = location.getAltitude();
                }
                mapLocationData.setValue(currentData);
            }
        });
    }

    public LiveData<MapLocationData> getMapLocationData() {
        return mapLocationData;
    }

    public void searchCurrentLocation() throws SecurityException {
        toSearchLocation.setValue(true);
    }
}
