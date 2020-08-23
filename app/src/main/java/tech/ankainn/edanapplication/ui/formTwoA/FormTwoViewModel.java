package tech.ankainn.edanapplication.ui.formTwoA;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.HouseholdData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;

public class FormTwoViewModel extends ViewModel {

    private FormTwoRepository formTwoRepository;

    private MediatorLiveData<HouseholdData> householdData = new MediatorLiveData<>();

    public FormTwoViewModel(FormTwoRepository formTwoRepository) {
        this.formTwoRepository = formTwoRepository;

        LiveData<FormTwoData> source = formTwoRepository.getCurrentFormTwoData();

        householdData.addSource(source, formTwoData -> {
            if(formTwoData != null && formTwoData.householdData != null) {
                householdData.setValue(formTwoData.householdData);
            }
        });
    }

    public void setFormTwoId(long formTwoId) {
        formTwoRepository.loadFormTwoData(formTwoId);
    }

    public LiveData<HouseholdData> getHouseholdData() {
        return householdData;
    }

    public void saveFormTwo() {
        formTwoRepository.saveForm();
        formTwoRepository.clearForm();
    }

    public void clearFormTwo() {
        formTwoRepository.clearForm();
    }
}
