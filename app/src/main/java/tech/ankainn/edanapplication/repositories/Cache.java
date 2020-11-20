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

    private MutableLiveData<List<Pair<Long, Boolean>>> formTwoLoading = new MutableLiveData<>(null);
    private MutableLiveData<List<Pair<Long, Boolean>>> formOneLoading = new MutableLiveData<>(null);

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

    public LiveData<List<Pair<Long, Boolean>>> getFormTwoLoading() {
        return formTwoLoading;
    }
    public LiveData<List<Pair<Long, Boolean>>> getFormOneLoading() {
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

    public void setFormTwoLoading(List<Pair<Long, Boolean>> list) {
        this.formTwoLoading.postValue(list);
    }
    public void setFormOneLoading(List<Pair<Long, Boolean>> list) {
        this.formOneLoading.postValue(list);
    }
}