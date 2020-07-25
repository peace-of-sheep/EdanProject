package tech.ankainn.edanapplication.ui.geninfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.android.gms.maps.model.LatLng;

import tech.ankainn.edanapplication.model.formTwo.MapLocationData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.ui.common.BaseViewModel;
import timber.log.Timber;

public class MapViewModel extends BaseViewModel {

    private final LatLng defaultLatLng = new LatLng(-7.146,-75.009);

    private MutableLiveData<MapLocationData> mapLocationData = new MutableLiveData<>();

    public MapViewModel(@NonNull Application application) {
        super(application);
        FormTwoRepository formTwoRepository = getApplication().getFormTwoRepository();
        if (formTwoRepository.hasCurrentData()) {
            mapLocationData.setValue(formTwoRepository.getCurrentData().mapLocationData);
        } else {
            MapLocationData data = new MapLocationData();
            data.latitude = defaultLatLng.latitude;
            data.longitude = defaultLatLng.longitude;
            mapLocationData.setValue(data);
        }
    }

    public LatLng getCurrentLatLng() {
        MapLocationData temp = mapLocationData.getValue();
        return temp == null ? defaultLatLng : new LatLng(temp.latitude, temp.longitude);
    }

    public LiveData<MapLocationData> getMapLocationData() {
        return mapLocationData;
    }

    public void setCentreLatLng(LatLng center) {
        MapLocationData temp = mapLocationData.getValue();
        temp.latitude = center.latitude;
        temp.longitude = center.longitude;
    }

    public MapLocationData requestData() {
        return mapLocationData.getValue();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.tag("ViewModel").d("onCleared: MapViewModel");
    }

    public void setData(MapLocationData data) {
        mapLocationData.setValue(data);
    }
}
