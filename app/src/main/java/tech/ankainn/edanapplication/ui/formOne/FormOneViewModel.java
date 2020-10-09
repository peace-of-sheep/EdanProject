package tech.ankainn.edanapplication.ui.formOne;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.model.app.formOne.FormOneData;
import tech.ankainn.edanapplication.repositories.FormOneRepository;

public class FormOneViewModel extends ViewModel {

    private FormOneRepository formOneRepository;

    private LiveData<FormOneData> formOneData;

    public FormOneViewModel(FormOneRepository formOneRepository) {
        this.formOneRepository = formOneRepository;
        formOneData = formOneRepository.getCurrentFormOneData();
    }

    public void setFormOneId(long formOneId) {
        formOneRepository.loadFormOneDataById(formOneId);
    }

    public void saveFormOne() {
        formOneRepository.saveCurrentFormOneData();
        formOneRepository.clearCurrentFormOneData();
    }

    public void clearFormOne() {
        formOneRepository.clearCurrentFormOneData();
    }
}
