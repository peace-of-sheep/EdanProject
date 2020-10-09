package tech.ankainn.edanapplication.ui.formTwoA;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.formTwo.HouseholdData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;

public class FormTwoViewModel extends ViewModel {

    private final int arrayUseRes = R.array.household_use;
    private final int arrayConditionRes = R.array.household_condition;
    private final int arrayRoofRes = R.array.roof_types;
    private final int arrayWallRes = R.array.wall_types;
    private final int arrayFloorRes = R.array.floor_types;

    private FormTwoRepository formTwoRepository;

    private LiveData<HouseholdData> householdData;
    private HouseholdData currentData;

    public FormTwoViewModel(FormTwoRepository formTwoRepository) {
        this.formTwoRepository = formTwoRepository;

        LiveData<HouseholdData> source = formTwoRepository.loadHouseholdData();
        householdData = Transformations.map(source, householdData -> {
            this.currentData = householdData;
            return householdData;
        });
    }

    public void setFormTwoId(long formTwoId) {
        formTwoRepository.loadFormTwoData(formTwoId);
    }

    public LiveData<HouseholdData> getHouseholdData() {
        return householdData;
    }

    public void setHouseholdUse(Context context, int pos) {
        String[] data = getDataFromResource(context, arrayUseRes);
        currentData.useHouse = data[pos];
        currentData.codeUseHouse = pos + 1;
    }

    public void setHouseholdCondition(Context context, int pos) {
        String[] data = getDataFromResource(context, arrayConditionRes);
        currentData.conditionHouse = data[pos];
        currentData.codeConditionHouse = pos + 1;
    }

    public void setTypeRoof(Context context, int pos) {
        String[] data = getDataFromResource(context, arrayRoofRes);
        currentData.typeRoof = data[pos];
        currentData.codeRoof = pos + 1;
    }

    public void setTypeWall(Context context, int pos) {
        String[] data = getDataFromResource(context, arrayWallRes);
        currentData.typeWall = data[pos];
        currentData.codeWall = pos + 1;
    }

    public void setTypeFloor(Context context, int pos) {
        String[] data = getDataFromResource(context, arrayFloorRes);
        currentData.typeFloor = data[pos];
        currentData.codeFloor = pos + 1;
    }

    private String[] getDataFromResource(Context context, int arrayRes) {
        return context.getResources().getStringArray(arrayRes);
    }

    public void saveFormTwo() {
        formTwoRepository.saveForm();
        formTwoRepository.clearForm();
    }

    public void clearFormTwo() {
        formTwoRepository.clearForm();
    }
}
