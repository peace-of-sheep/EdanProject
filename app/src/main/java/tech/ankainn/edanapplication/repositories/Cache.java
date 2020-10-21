package tech.ankainn.edanapplication.repositories;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.app.auth.UserData;
import tech.ankainn.edanapplication.model.app.formOne.FormOneData;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;

public class Cache {

    private static Cache instance = new Cache();

    private MutableLiveData<UserData> userData = new MutableLiveData<>();

    private MutableLiveData<GenInfData> genInfData = new MutableLiveData<>();
    private MutableLiveData<FormTwoData> formTwoData = new MutableLiveData<>();
    private MutableLiveData<FormOneData> formOneData = new MutableLiveData<>();

    private MutableLiveData<List<Long>> formTwoLoading = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<Long>> formOneLoading = new MutableLiveData<>(new ArrayList<>());

    public static Cache getInstance() {
        return instance;
    }

    private Cache() {
    }

    public LiveData<UserData> getUserData() {
        return userData;
    }
    public LiveData<FormTwoData> getFormTwoData() {
        return formTwoData;
    }
    public LiveData<FormOneData> getFormOneData() {
        return formOneData;
    }
    public LiveData<GenInfData> getGenInfData() {
        return genInfData;
    }

    public LiveData<List<Long>> getFormTwoLoading() {
        return formTwoLoading;
    }
    public LiveData<List<Long>> getFormOneLoading() {
        return formOneLoading;
    }

    public void setUserData(UserData userData) {
        this.userData.postValue(userData);
    }
    public void setFormTwoData(FormTwoData formTwoData) {
        this.formTwoData.postValue(formTwoData);
    }
    public void setFormOneData(FormOneData formOneData) {
        this.formOneData.postValue(formOneData);
    }
    public void setGenInfData(GenInfData genInfData) {
        this.genInfData.postValue(genInfData);
    }

    public void setFormTwoLoading(List<Long> list) {
        this.formTwoLoading.postValue(list);
    }
    public void setFormOneLoading(List<Long> list) {
        this.formOneLoading.postValue(list);
    }
}