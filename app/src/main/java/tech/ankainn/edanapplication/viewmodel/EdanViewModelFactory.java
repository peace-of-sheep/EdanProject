package tech.ankainn.edanapplication.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.repositories.FormOneRepository;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.repositories.GenInfRepository;
import tech.ankainn.edanapplication.repositories.UserRepository;
import tech.ankainn.edanapplication.ui.auth.LoginViewModel;
import tech.ankainn.edanapplication.ui.formOne.FormOneViewModel;
import tech.ankainn.edanapplication.ui.formOne.SwitchableViewModel;
import tech.ankainn.edanapplication.ui.formTwoA.FormTwoViewModel;
import tech.ankainn.edanapplication.ui.formTwoA.MembersViewModel;
import tech.ankainn.edanapplication.ui.formTwoB.LivelihoodViewModel;
import tech.ankainn.edanapplication.ui.geninfo.ExtraViewModel;
import tech.ankainn.edanapplication.ui.geninfo.HeaderViewModel;
import tech.ankainn.edanapplication.ui.geninfo.MapLocationViewModel;
import tech.ankainn.edanapplication.ui.host.FilesViewModel;

public class EdanViewModelFactory implements ViewModelProvider.Factory {

    private UserRepository userRepository;
    private GenInfRepository genInfRepository;
    private FormOneRepository formOneRepository;
    private FormTwoRepository formTwoRepository;

    public EdanViewModelFactory(UserRepository userRepository,
                                GenInfRepository genInfRepository,
                                FormOneRepository formOneRepository,
                                FormTwoRepository formTwoRepository) {
        this.userRepository = userRepository;
        this.genInfRepository = genInfRepository;
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
        if (modelClass == HeaderViewModel.class)
            return (T) new HeaderViewModel(genInfRepository);
        if (modelClass == ExtraViewModel.class)
            return (T) new ExtraViewModel(genInfRepository);
        if (modelClass == MapLocationViewModel.class)
            return (T) new MapLocationViewModel(genInfRepository);
        if (modelClass == FormOneViewModel.class)
            return (T) new FormOneViewModel(formOneRepository);
        if (modelClass == SwitchableViewModel.class)
            return (T) new SwitchableViewModel(formOneRepository);
        if (modelClass == FormTwoViewModel.class)
            return (T) new FormTwoViewModel(formTwoRepository);
        if (modelClass == LivelihoodViewModel.class)
            return (T) new LivelihoodViewModel(formTwoRepository);
        if (modelClass == MembersViewModel.class)
            return (T) new MembersViewModel(formTwoRepository);
        throw new RuntimeException("Don't use this factory");
    }
}
