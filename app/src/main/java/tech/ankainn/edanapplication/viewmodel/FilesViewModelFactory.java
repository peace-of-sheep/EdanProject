package tech.ankainn.edanapplication.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.repositories.FormOneRepository;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.ui.host.FilesViewModel;

public class FilesViewModelFactory implements ViewModelProvider.Factory {

    private FormOneRepository formOneRepository;
    private FormTwoRepository formTwoRepository;

    public FilesViewModelFactory(FormOneRepository formOneRepository, FormTwoRepository formTwoRepository) {
        this.formOneRepository = formOneRepository;
        this.formTwoRepository = formTwoRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == FilesViewModel.class)
            return (T) new FilesViewModel(formOneRepository, formTwoRepository);
        throw new RuntimeException("Check viewModel dependant");
    }
}
