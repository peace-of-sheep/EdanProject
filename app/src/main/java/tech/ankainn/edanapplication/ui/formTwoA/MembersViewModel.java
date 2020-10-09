package tech.ankainn.edanapplication.ui.formTwoA;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.model.api.ReniecData;
import tech.ankainn.edanapplication.model.factory.FormTwoFactory;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;

public class MembersViewModel extends ViewModel {

    private final int arrayIdRes = R.array.id_types;
    private final int arrayGenderRes = R.array.gender;
    private final int arrayConditionRes = R.array.condition;
    private final int arrayInjuryRes = R.array.personal_injury;

    private FormTwoRepository formTwoRepository;

    private LiveData<List<MemberData>> listMemberData;
    private List<MemberData> currentData;

    private LiveData<MemberData> dialogMember;
    private MemberData currentMemberData;

    private long countTempId = 0;

    private MutableLiveData<String> dni = new MutableLiveData<>();
    private LiveData<ReniecData> personReniecData;

    public MembersViewModel(FormTwoRepository formTwoRepository) {
        this.formTwoRepository = formTwoRepository;

        LiveData<List<MemberData>> source = formTwoRepository.loadListMemberData();
        listMemberData = Transformations.map(source, listMemberData -> {
            for (MemberData memberData : listMemberData) {
                memberData.tempId = countTempId++;
            }
            this.currentData = listMemberData;
            return listMemberData;
        });

        LiveData<MemberData> sourceMemberData = formTwoRepository.loadCurrentMemberData();
        dialogMember = Transformations.map(sourceMemberData, memberData -> {
            this.currentMemberData = memberData;
            return memberData;
        });

        personReniecData = Transformations.switchMap(dni, dni -> {
            if (dni == null) {
                return new MutableLiveData<>(null);
            } else {
                return formTwoRepository.searchPersonData(dni);
            }
        });
    }

    public LiveData<List<MemberData>> getListMemberData() {
        return listMemberData;
    }

    public LiveData<MemberData> getDialogMemberData() {
        return dialogMember;
    }

    public void createMemberData() {
        formTwoRepository.saveCacheMemberData(FormTwoFactory.createEmptyMemberData());
    }

    public void updateMemberData(long tempId) {
        for (MemberData memberData : currentData) {
            if (memberData.tempId == tempId) {
                MemberData toUpdateData = FormTwoFactory.cloneMemberData(memberData);
                formTwoRepository.saveCacheMemberData(toUpdateData);
            }
        }
    }

    public void clearMemberData() {
        formTwoRepository.clearCacheMemberData();
    }

    public void pushActiveMemberData() {
        currentMemberData.dataVersion++;
        formTwoRepository.pushCacheMemberData(currentMemberData);
    }

    public void searchPersonByDni(String dni) {
        this.dni.setValue(dni);
    }

    public LiveData<ReniecData> getPersonReniecData() {
        return personReniecData;
    }

    public void setDocumentType(Context context, int pos) {
        String[] documentType = getDataFromResource(context, arrayIdRes);
        currentMemberData.typeIdentification = documentType[pos];
        currentMemberData.codeIdentification = pos + 1;
    }
    public void setGender(Context context, int pos) {
        String[] genders = getDataFromResource(context, arrayGenderRes);
        currentMemberData.gender = genders[pos];
        currentMemberData.codeGender = String.valueOf(genders[pos].charAt(0));
    }
    public void setCondition(Context context, int pos) {
        String[] conditions = getDataFromResource(context, arrayConditionRes);
        currentMemberData.condition = conditions[pos];
        currentMemberData.codeCondition = Integer.toString(pos);
    }
    public void setInjury(Context context, int pos) {
        String[] injuries = getDataFromResource(context, arrayInjuryRes);
        currentMemberData.personalInjury = injuries[pos];
        currentMemberData.codePersonalInjury = Integer.toString(pos);
    }

    private String[] getDataFromResource(Context context, int arrayRes) {
        return context.getResources().getStringArray(arrayRes);
    }
}
