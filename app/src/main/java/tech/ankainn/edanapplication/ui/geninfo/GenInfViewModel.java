package tech.ankainn.edanapplication.ui.geninfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;
import tech.ankainn.edanapplication.repositories.GenInfRepository;

public class GenInfViewModel extends ViewModel {

    private MediatorLiveData<MapLocationData> mapLocationData = new MediatorLiveData<>();
    private MediatorLiveData<GenInfData> genInfData = new MediatorLiveData<>();

    public GenInfViewModel(GenInfRepository genInfRepository) {
        LiveData<MapLocationData> sourceMapLocation = genInfRepository.getMapLocationData();
        mapLocationData.addSource(sourceMapLocation, mapLocationData -> {
            if (mapLocationData != null) {
                this.mapLocationData.setValue(mapLocationData);
            }
        });

        LiveData<GenInfData> sourceGenInfData = genInfRepository.getGenInfData();
        genInfData.addSource(sourceGenInfData, genInfData -> {
            if (genInfData != null) {
                this.genInfData.setValue(genInfData);
            }
        });
    }

    public LiveData<MapLocationData> getMapLocationData() {
        return mapLocationData;
    }

    public LiveData<GenInfData> getGenInfData() {
        return genInfData;
    }
}
