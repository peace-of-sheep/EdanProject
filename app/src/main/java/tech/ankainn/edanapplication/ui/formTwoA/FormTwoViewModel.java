package tech.ankainn.edanapplication.ui.formTwoA;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.HouseholdData;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.ui.common.BaseViewModel;
import tech.ankainn.edanapplication.model.factory.FormTwoFactory;
import tech.ankainn.edanapplication.util.Tuple2;

import static tech.ankainn.edanapplication.ui.formTwoA.FormTwoViewModel.PostState.CREATION;
import static tech.ankainn.edanapplication.ui.formTwoA.FormTwoViewModel.PostState.UPDATE;

public class FormTwoViewModel extends BaseViewModel {

    private FormTwoRepository formTwoRepository;

    private MediatorLiveData<HouseholdData> householdData = new MediatorLiveData<>();
    private MediatorLiveData<List<MemberData>> copyList = new MediatorLiveData<>();
    private MutableLiveData<Tuple2<PostState, MemberData>> activeMemberData = new MutableLiveData<>();

    private List<MemberData> mutableOriginalList;

    private long countTempId = 0;

    public FormTwoViewModel(@NonNull Application application) {
        super(application);
        formTwoRepository = getApplication().getFormTwoRepository();

        LiveData<FormTwoData> source = formTwoRepository.getCurrentFormTwoData();

        householdData.addSource(source, formTwoData -> {
            if(formTwoData == null || formTwoData.householdData == null) {
                throw new RuntimeException("Empty householdData not allowed");
            }
            householdData.setValue(formTwoData.householdData);
        });
        copyList.addSource(source, formTwoData -> {
            if(formTwoData == null || formTwoData.listMemberData == null) {
                throw new RuntimeException("Empty listMemberData not allowed");
            }
            mutableOriginalList = formTwoData.listMemberData;

            for (MemberData memberData : mutableOriginalList) {
                memberData.tempId = ++countTempId;
            }

            copyList.setValue(new ArrayList<>(mutableOriginalList));
        });
    }

    public LiveData<HouseholdData> getHouseholdData() {
        return householdData;
    }

    public LiveData<List<MemberData>> getListMemberData() {
        return copyList;
    }

    public LiveData<MemberData> getActiveMemberData() {
        return Transformations.map(activeMemberData, tuple2 -> tuple2.second);
    }

    public MemberData getTempHead() {
        MemberData memberData = FormTwoFactory.createEmptyMemberData();
        this.activeMemberData.setValue(new Tuple2<>(CREATION, memberData));
        return memberData;
    }

    public void createMember() {
        activeMemberData.setValue(new Tuple2<>(CREATION, FormTwoFactory.createEmptyMemberData()));
    }

    public void updateMember(MemberData memberData) {
        MemberData update = FormTwoFactory.cloneMemberData(memberData);
        activeMemberData.setValue(new Tuple2<>(UPDATE, update));
    }

    @SuppressWarnings("ConstantConditions")
    public void pushActiveMemberData() {
        Tuple2<PostState, MemberData> tuple2 = activeMemberData.getValue();
        tuple2.second.dataVersion++;

        if (tuple2.first == CREATION) {
            tuple2.second.tempId = ++countTempId;
            mutableOriginalList.add(tuple2.second);

        } else if(tuple2.first == UPDATE) {
            for (int i = 0; i < mutableOriginalList.size(); i++) {
                if(mutableOriginalList.get(i).tempId == tuple2.second.tempId) {
                    mutableOriginalList.set(i, tuple2.second);
                    break;
                }
            }
        }

        copyList.setValue(new ArrayList<>(mutableOriginalList));
    }

    public void saveFile() {
        formTwoRepository.saveForm();
    }

    enum PostState {
        CREATION,
        UPDATE
    }
}