package tech.ankainn.edanapplication.ui.geninfo;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.model.app.geninf.MapLocationData;
import tech.ankainn.edanapplication.repositories.GenInfRepository;
import tech.ankainn.edanapplication.util.Tuple2;

public class MapLocationViewModel extends ViewModel {

    private GenInfRepository genInfRepository;

    private MutableLiveData<State> state = new MutableLiveData<>();
    private MediatorLiveData<MapLocationData> mapLocationData = new MediatorLiveData<>();
    private MapLocationData currentData;

    @SuppressLint("MissingPermission")
    public MapLocationViewModel(GenInfRepository genInfRepository) {
        this.genInfRepository = genInfRepository;

        state.setValue(State.STILL);

        LiveData<MapLocationData> source = genInfRepository.loadMapLocationData();
        mapLocationData.addSource(source, mapLocationData -> {
            this.currentData = mapLocationData;
            this.mapLocationData.setValue(mapLocationData);
        });
    }

    public LiveData<MapLocationData> getMapLocationData() {
        return mapLocationData;
    }

    public LiveData<State> getState() {
        return state;
    }

    public void updateLatLng(double latitude, double longitude) {
        if (currentData != null) {
            currentData.latitude = latitude;
            currentData.longitude = longitude;
        }
    }

    public void searchCurrentLocation() throws SecurityException {
        state.setValue(State.LOADING);
        LiveData<Tuple2<Double, Double>> source = genInfRepository.loadLastLocation();
        mapLocationData.addSource(source, tuple -> {
            mapLocationData.removeSource(source);
            state.setValue(State.STILL);
            if (tuple != null) {
                updateLatLng(tuple.first, tuple.second);
                mapLocationData.setValue(currentData);
            }
        });
    }

    public enum State {
        STILL,
        LOADING
    }
}
