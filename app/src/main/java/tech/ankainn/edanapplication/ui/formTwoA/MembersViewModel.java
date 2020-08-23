package tech.ankainn.edanapplication.ui.formTwoA;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.factory.FormTwoFactory;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;

public class MembersViewModel extends ViewModel {

    private MediatorLiveData<List<MemberData>> copyList = new MediatorLiveData<>();

    private MutableLiveData<Long> tempId = new MutableLiveData<>();
    private MediatorLiveData<MemberData> dialogMember = new MediatorLiveData<>();

    private List<MemberData> originalList;

    private long countTempId = 0;

    public MembersViewModel(FormTwoRepository formTwoRepository) {
        LiveData<FormTwoData> source = formTwoRepository.getCurrentFormTwoData();

        copyList.addSource(source, formTwoData -> {
            if(formTwoData != null && formTwoData.listMemberData != null) {

                originalList = formTwoData.listMemberData;

                for (MemberData memberData : originalList) {
                    memberData.tempId = ++countTempId;
                }

                copyList.setValue(new ArrayList<>(originalList));
            }
        });

        dialogMember.addSource(tempId, tempId -> {
            if (tempId == 0L) {
                MemberData memberData = FormTwoFactory.createEmptyMemberData();
                dialogMember.setValue(memberData);
            } else {
                for (MemberData memberData : originalList) {
                    if (tempId == memberData.tempId) {
                        MemberData update = FormTwoFactory.cloneMemberData(memberData);
                        dialogMember.setValue(update);
                        return;
                    }
                }
            }
        });
    }

    public void searchTempId(long tempId) {
        this.tempId.setValue(tempId);
    }

    public LiveData<List<MemberData>> getListMemberData() {
        return copyList;
    }

    public LiveData<MemberData> getDialogMemberData() {
        return dialogMember;
    }

    public void pushActiveMemberData() {
        MemberData memberData = dialogMember.getValue();
        if (memberData == null) return;

        memberData.dataVersion++;

        if (memberData.tempId == 0L) {
            memberData.tempId = ++countTempId;
            originalList.add(memberData);
        } else {
            for (int i = 0; i < originalList.size(); i++) {
                if (originalList.get(i).tempId == memberData.tempId) {
                    originalList.set(i, memberData);
                    break;
                }
            }
        }

        copyList.setValue(new ArrayList<>(originalList));
    }

    public MemberData getTempHead() {
        MemberData memberData = FormTwoFactory.createEmptyMemberData();
        dialogMember.setValue(memberData);
        return memberData;
    }
}
