package tech.ankainn.edanapplication.ui.formTwoA;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.binding.Converter;
import tech.ankainn.edanapplication.model.api.ReniecData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.repositories.MemberRepository;
import tech.ankainn.edanapplication.repositories.ReniecRepository;
import tech.ankainn.edanapplication.util.SingleLiveData;

public class MemberViewModel extends ViewModel {

    private final int arrayIdRes = R.array.id_types;
    private final int arrayGenderRes = R.array.gender;
    private final int arrayConditionRes = R.array.condition;
    private final int arrayInjuryRes = R.array.personal_injury;

    private MemberRepository memberRepository;

    private LiveData<List<MemberData>> listMemberData;
    private List<MemberData> currentData;

    private MutableLiveData<Boolean> householdCondition = new MutableLiveData<>();

    private MutableLiveData<String> identificationNumber = new MutableLiveData<>();
    private MutableLiveData<Long> tempId = new MutableLiveData<>();

    private MediatorLiveData<MemberData> memberData = new MediatorLiveData<>();
    private MemberData currentMemberData;
    private MutableLiveData<State> state = new MutableLiveData<>();
    private SingleLiveData<State> singleEvent = new SingleLiveData<>();

    public MemberViewModel(MemberRepository memberRepository, ReniecRepository reniecRepository) {
        this.memberRepository = memberRepository;

        LiveData<List<MemberData>> source = memberRepository.loadListMemberData();
        listMemberData = Transformations.map(source, listMemberData -> {
            this.currentData = listMemberData;
            return listMemberData;
        });

        memberData.addSource(tempId, tempId -> {
            MemberData memberData = memberRepository.loadMemberData(tempId);
            if (memberData != null) {
                this.currentMemberData = memberData;
                if (memberRepository.checkHouseholdAffected()) {
                    currentMemberData.condition = "Afectado";
                    currentMemberData.codeCondition = "2";
                }
                this.memberData.setValue(memberData);
            }
        });

        householdCondition.setValue(memberRepository.checkHouseholdAffected());

        LiveData<ReniecData> sourceReniecData = Transformations.switchMap(identificationNumber,
                reniecRepository::searchPersonData);
        memberData.addSource(sourceReniecData, reniecData -> {
            if (reniecData != null && currentMemberData != null) {

                String[] rawSurname = reniecData.apellidos.split(",");
                String surname = rawSurname[0]+rawSurname[1];

                String gender = Objects.equals(reniecData.tipoSexo, "M") ? "Masculino" : "Femenino";

                currentMemberData.surname = surname;
                currentMemberData.name = reniecData.nombres;
                currentMemberData.age = Converter.stringToInteger(reniecData.edad);
                currentMemberData.birthdate = reniecData.birthdate;
                currentMemberData.gender = gender;
                currentMemberData.codeGender = reniecData.tipoSexo;

                memberData.setValue(currentMemberData);
                state.setValue(State.SUCCESSFUL);
            } else {
                state.setValue(State.ERROR);
            }
        });

        singleEvent.addSource(state, state -> singleEvent.setValue(state));
    }

    public LiveData<List<MemberData>> getListMemberData() {
        return listMemberData;
    }
    public LiveData<MemberData> getMemberData() {
        return memberData;
    }

    public LiveData<State> getState() {
        return state;
    }
    public LiveData<State> getSingleEvent() {
        return singleEvent;
    }

    public void loadMemberData(long tempId) {
        if (memberData.getValue() == null) {
            this.tempId.setValue(tempId);
        }
    }

    public boolean searchPersonByDni() {
        if (currentMemberData == null) return false;

        Integer code = currentMemberData.codeIdentification;
        Integer idNumber = currentMemberData.textIdentification;
        if (code == 1 && idNumber != null) {
            this.identificationNumber.setValue(Integer.toString(idNumber));
            this.state.setValue(State.LOADING);
            return true;
        } else {
            return false;
        }
    }

    public boolean saveMemberData() {
        if (currentMemberData != null && currentMemberData.notEmpty()) {
            currentMemberData.dataVersion++;
            memberRepository.saveMemberData(currentMemberData);
            return true;
        }
        return false;
    }

    public void setDocumentType(Context context, int pos) {
        if (currentMemberData == null) return;

        String[] documentType = getDataFromResource(context, arrayIdRes);
        currentMemberData.typeIdentification = documentType[pos];
        currentMemberData.codeIdentification = pos + 1;
    }
    public void setGender(Context context, int pos) {
        if (currentMemberData == null) return;

        String[] genders = getDataFromResource(context, arrayGenderRes);
        currentMemberData.gender = genders[pos];
        currentMemberData.codeGender = String.valueOf(genders[pos].charAt(0));
    }
    public void setCondition(Context context, int pos) {
        if (currentMemberData == null) return;

        String[] conditions = getDataFromResource(context, arrayConditionRes);
        currentMemberData.condition = conditions[pos];
        currentMemberData.codeCondition = Integer.toString(pos + 1);
    }
    public void setInjury(Context context, int pos) {
        if (currentMemberData == null) return;

        String[] injuries = getDataFromResource(context, arrayInjuryRes);
        currentMemberData.personalInjury = injuries[pos];
        currentMemberData.codePersonalInjury = Integer.toString(pos + 1);
    }

    private String[] getDataFromResource(Context context, int arrayRes) {
        return context.getResources().getStringArray(arrayRes);
    }

    public LiveData<Boolean> getHouseholdCondition() {
        return householdCondition;
    }

    public enum State {
        STILL,
        LOADING,
        ERROR,
        SUCCESSFUL
    }
}
