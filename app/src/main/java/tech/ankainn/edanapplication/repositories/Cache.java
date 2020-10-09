package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import tech.ankainn.edanapplication.model.app.formOne.FormOneData;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;

public class Cache {

    private static Cache instance = new Cache();

    private MutableLiveData<GenInfData> genInfDataLiveData;

    private MutableLiveData<MemberData> memberDataLiveData;

    private MutableLiveData<FormTwoData> formTwoDataLiveData;
    private MutableLiveData<FormOneData> formOneDataLiveData;

    public static Cache getInstance() {
        return instance;
    }

    private Cache() {
        formTwoDataLiveData = new MutableLiveData<>();
        formOneDataLiveData = new MutableLiveData<>();
        genInfDataLiveData = new MutableLiveData<>();
        memberDataLiveData = new MutableLiveData<>();
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

    public LiveData<MemberData> getMemberData() {
        return memberDataLiveData;
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

    public void setMemberData(MemberData memberData) {
        memberDataLiveData.postValue(memberData);
    }
}