package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import tech.ankainn.edanapplication.model.formOne.FormOneData;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class Cache {

    private static Cache instance = new Cache();

    private MutableLiveData<GenInfData> genInfDataLiveData;
    private MutableLiveData<MapLocationData> mapLocationDataLiveData;

    private MutableLiveData<FormTwoData> formTwoDataLiveData;

    private MutableLiveData<FormOneData> formOneDataLiveData;

    public static Cache getInstance() {
        return instance;
    }

    private Cache() {
        formTwoDataLiveData = new MutableLiveData<>();
        formOneDataLiveData = new MutableLiveData<>();
        genInfDataLiveData = new MutableLiveData<>();
        mapLocationDataLiveData = new MutableLiveData<>();
    }

    public LiveData<FormTwoData> getFormTwoData() {
        return formTwoDataLiveData;
    }

    public LiveData<FormOneData> getFormOneData() {
        return formOneDataLiveData;
    }

    public LiveData<GenInfData> getGenInfData() {
        return genInfDataLiveData;
    }

    public LiveData<MapLocationData> getMapLocationData() {
        return mapLocationDataLiveData;
    }

    public void setFormTwoData(FormTwoData formTwoData) {
        formTwoDataLiveData.postValue(formTwoData);
    }

    public void setFormOneData(FormOneData formOneData) {
        formOneDataLiveData.postValue(formOneData);
    }

    public void setGenInfData(GenInfData genInfData) {
        genInfDataLiveData.postValue(genInfData);
    }

    public void setMapLocationData(MapLocationData mapLocationData) {
        mapLocationDataLiveData.postValue(mapLocationData);
    }
}