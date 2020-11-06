package tech.ankainn.edanapplication.ui.formOne;

import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.repositories.FormOneRepository;

public class FormOneViewModel extends ViewModel {

    private FormOneRepository formOneRepository;

    public FormOneViewModel(FormOneRepository formOneRepository) {
        this.formOneRepository = formOneRepository;
    }

    public void loadFormOne(long formOneId, long userId) {
        formOneRepository.loadFormOneDataById(formOneId, userId);
    }

    public void saveFormOne() {
        formOneRepository.saveFormOneData();
        formOneRepository.clearCacheFormOneData();
    }

    public void clearFormOne() {
        formOneRepository.clearCacheFormOneData();
    }
}
