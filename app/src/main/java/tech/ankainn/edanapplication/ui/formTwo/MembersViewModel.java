package tech.ankainn.edanapplication.ui.formTwo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.formTwo.MemberData;
import timber.log.Timber;

public class MembersViewModel extends ViewModel {
    private MutableLiveData<List<MemberData>> list = new MutableLiveData<>();
    private MutableLiveData<MemberData> householdHead = new MutableLiveData<>();

    private MemberData tempHead = new MemberData();

    public MembersViewModel() {
        Timber.tag("ViewModel").i("MembersViewModel:");
    }

    public LiveData<MemberData> getHouseholdHead() {
        return householdHead;
    }

    public boolean isHouseholdHedDataComplete() {
        return tempHead.checkData();
    }

    public void addHouseholdHead() {
        householdHead.setValue(tempHead);
    }

    public MemberData getTempHead() {
        tempHead.head = true;
        return tempHead;
    }

    public LiveData<List<MemberData>> getList() {
        return list;
    }

    public void addHouseholdMember(MemberData member) {
        List<MemberData> temp = list.getValue() == null ? new ArrayList<>() : new ArrayList<>(list.getValue());
        temp.add(member);
        list.setValue(temp);
    }

    public List<MemberData> requestData() {
        List<MemberData> result = new ArrayList<>();
        result.add(householdHead.getValue());
        if(list.getValue() != null) result.addAll(list.getValue());
        return result;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.tag("ViewModel").d("onCleared: MembersViewModel");
    }
}

