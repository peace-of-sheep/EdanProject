package tech.ankainn.edanapplication.ui.geninfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.android.gms.maps.model.LatLng;

import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.ui.common.BaseViewModel;

public class GenInfViewModel extends BaseViewModel {

    private LatLng defaultLatLng = new LatLng(-7.146,-75.009);

    private MediatorLiveData<MapLocationData> mapLocationData = new MediatorLiveData<>();
    private MediatorLiveData<GenInfData> genInfData = new MediatorLiveData<>();

    public GenInfViewModel(@NonNull Application application) {
        super(application);
        FormTwoRepository formTwoRepository = getApplication().getFormTwoRepository();

        LiveData<FormTwoData> source = formTwoRepository.getCurrentFormTwoData();
        mapLocationData.addSource(source, formTwoData -> {
            if (formTwoData == null || formTwoData.mapLocationData == null) {
                throw new RuntimeException("Empty mapLocationData not allowed");
            }
            if (formTwoData.dataVersion == 0) {
                formTwoData.mapLocationData.latitude = defaultLatLng.latitude;
                formTwoData.mapLocationData.longitude = defaultLatLng.longitude;
            }
            mapLocationData.setValue(formTwoData.mapLocationData);
        });
        genInfData.addSource(source, formTwoData -> {
            if (formTwoData == null || formTwoData.genInfData == null) {
                throw new RuntimeException("Empty genInfoData not allowed");
            }
            genInfData.setValue(formTwoData.genInfData);
        });

    }

    public LiveData<MapLocationData> getMapLocationData() {
        return mapLocationData;
    }

    public LiveData<GenInfData> getGenInfData() {
        return genInfData;
    }
}
