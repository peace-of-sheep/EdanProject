package tech.ankainn.edanapplication.ui.formTwo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.android.gms.maps.model.LatLng;

import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.HouseholdData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.ui.common.BaseViewModel;
import tech.ankainn.edanapplication.ui.geninfo.FormViewModel;
import tech.ankainn.edanapplication.util.FormTwoFactory;
import timber.log.Timber;

public class FormTwoViewModel extends BaseViewModel implements FormViewModel {

    private FormTwoRepository formTwoRepository;

    private LatLng defaultLatLng = new LatLng(-7.146,-75.009);

    private MediatorLiveData<MapLocationData> mapLocationData = new MediatorLiveData<>();
    private MediatorLiveData<GenInfData> genInfData = new MediatorLiveData<>();
    private MediatorLiveData<HouseholdData> householdData = new MediatorLiveData<>();

    public FormTwoViewModel(@NonNull Application application) {
        super(application);

        formTwoRepository = getApplication().getFormTwoRepository();
        LiveData<FormTwoData> source = formTwoRepository.getCurrentFormTwoData();

        mapLocationData.addSource(source, formTwoData -> {
            if (formTwoData == null) {
                MapLocationData data = new MapLocationData();
                data.latitude = defaultLatLng.latitude;
                data.longitude = defaultLatLng.longitude;
                mapLocationData.setValue(data);
            } else {
                mapLocationData.setValue(formTwoData.mapLocationData);
            }
        });
        genInfData.addSource(source, formTwoData ->
                genInfData.setValue(formTwoData == null ? new GenInfData() : formTwoData.genInfData));

        householdData.addSource(source, formTwoData ->
                householdData.setValue(formTwoData == null ? new HouseholdData() : formTwoData.householdData));
    }

    @Override
    public LiveData<MapLocationData> getMapLocationData() {
        return mapLocationData;
    }

    @Override
    public LiveData<GenInfData> getGenInfData() {
        return genInfData;
    }

    public LiveData<HouseholdData> getHouseholdData() {
        return householdData;
    }

    public void setIdFloor(int position) {
        Timber.v("setIdFloor: %s", position);
        householdData.getValue().idFloor = position;
    }
    public void setIdWall(int position) {
        Timber.v("setIdWall: %s", position);
        householdData.getValue().idWall = position;
    }
    public void setIdRoof(int position) {
        Timber.v("setIdRoof: %s", position);
        householdData.getValue().idRoof = position;
    }

    public void saveFile() {
        MapLocationData mapLocationData = this.mapLocationData.getValue();
        GenInfData genInfData = this.genInfData.getValue();
        HouseholdData householdData = this.householdData.getValue();
        FormTwoData formTwoData = FormTwoFactory.create(genInfData, mapLocationData, householdData, null);

        formTwoRepository.saveForm(formTwoData);
    }
}
