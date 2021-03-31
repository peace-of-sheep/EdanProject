package tech.ankainn.edanapplication.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.repositories.DataRepository;
import tech.ankainn.edanapplication.repositories.FormOneRepository;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.repositories.UbigeoRepository;
import tech.ankainn.edanapplication.repositories.UserRepository;
import tech.ankainn.edanapplication.ui.auth.LoginViewModel;
import tech.ankainn.edanapplication.ui.host.FilesViewModel;
import tech.ankainn.edanapplication.ui.host.HostViewModel;
import tech.ankainn.edanapplication.ui.host.UserViewModel;

public class EdanViewModelFactory implements ViewModelProvider.Factory {

    private UserRepository userRepository;
    private DataRepository dataRepository;

    private FormOneRepository formOneRepository;
    private FormTwoRepository formTwoRepository;

    public EdanViewModelFactory(UserRepository userRepository,
                                DataRepository dataRepository,
                                FormOneRepository formOneRepository,
                                FormTwoRepository formTwoRepository) {
        this.userRepository = userRepository;
        this.dataRepository = dataRepository;
        this.formOneRepository = formOneRepository;
        this.formTwoRepository = formTwoRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == LoginViewModel.class)
            return (T) new LoginViewModel(userRepository, dataRepository);
        if (modelClass == HostViewModel.class)
            return (T) new HostViewModel(userRepository);
        if (modelClass == FilesViewModel.class)
            return (T) new FilesViewModel(formOneRepository, formTwoRepository, userRepository);
        if (modelClass == UserViewModel.class)
            return (T) new UserViewModel(userRepository);
        throw new RuntimeException("Don't use this factory");
    }
}
