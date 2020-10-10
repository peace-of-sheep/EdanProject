package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import tech.ankainn.edanapplication.model.app.auth.UserData;
import tech.ankainn.edanapplication.model.app.formOne.FormOneData;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;

public class Cache {

    private static Cache instance = new Cache();

    private MutableLiveData<UserData> userDataLiveData;

    private MutableLiveData<GenInfData> genInfDataLiveData;
    private MutableLiveData<FormTwoData> formTwoDataLiveData;
    private MutableLiveData<FormOneData> formOneDataLiveData;

    public static Cache getInstance() {
        return instance;
    }

    private Cache() {
        userDataLiveData = new MutableLiveData<>();
        formTwoDataLiveData = new MutableLiveData<>();
        formOneDataLiveData = new MutableLiveData<>();
        genInfDataLiveData = new MutableLiveData<>();
    }

    public LiveData<UserData> getUserData() {
        return userDataLiveData;
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

    public void setUserData(UserData userData) {
        this.userDataLiveData.postValue(userData);
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
}