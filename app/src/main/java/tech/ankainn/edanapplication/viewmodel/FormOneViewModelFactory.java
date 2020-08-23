package tech.ankainn.edanapplication.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.repositories.FormOneRepository;
import tech.ankainn.edanapplication.ui.formOne.FormOneViewModel;
import tech.ankainn.edanapplication.ui.formOne.SwitchableViewModel;

public class FormOneViewModelFactory implements ViewModelProvider.Factory {

    private FormOneRepository formOneRepository;

    public FormOneViewModelFactory(FormOneRepository formOneRepository) {
        this.formOneRepository = formOneRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == FormOneViewModel.class)
            return (T) new FormOneViewModel(formOneRepository);
        if (modelClass == SwitchableViewModel.class)
            return (T) new SwitchableViewModel(formOneRepository);
        throw new RuntimeException("Check viewmodel dependant");
    }
}
