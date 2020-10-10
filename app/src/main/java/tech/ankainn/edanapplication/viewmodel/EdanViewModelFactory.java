package tech.ankainn.edanapplication.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.repositories.FormOneRepository;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.repositories.UserRepository;
import tech.ankainn.edanapplication.ui.auth.LoginViewModel;
import tech.ankainn.edanapplication.ui.host.FilesViewModel;
import tech.ankainn.edanapplication.ui.host.UserViewModel;

public class EdanViewModelFactory implements ViewModelProvider.Factory {

    private UserRepository userRepository;

    private FormOneRepository formOneRepository;
    private FormTwoRepository formTwoRepository;

    public EdanViewModelFactory(UserRepository userRepository,
                                FormOneRepository formOneRepository,
                                FormTwoRepository formTwoRepository) {
        this.userRepository = userRepository;
        this.formOneRepository = formOneRepository;
        this.formTwoRepository = formTwoRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == LoginViewModel.class)
            return (T) new LoginViewModel(userRepository);
        if (modelClass == FilesViewModel.class)
            return (T) new FilesViewModel(formOneRepository, formTwoRepository);
        if (modelClass == UserViewModel.class)
            return (T) new UserViewModel(userRepository);
        throw new RuntimeException("Don't use this factory");
    }
}
