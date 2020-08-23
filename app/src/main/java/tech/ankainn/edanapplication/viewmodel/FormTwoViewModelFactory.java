package tech.ankainn.edanapplication.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.*;

import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.ui.formTwoA.FormTwoViewModel;
import tech.ankainn.edanapplication.ui.formTwoA.MembersViewModel;
import tech.ankainn.edanapplication.ui.formTwoB.LivelihoodViewModel;
import tech.ankainn.edanapplication.ui.host.FilesViewModel;

public class FormTwoViewModelFactory implements Factory {

    private FormTwoRepository formTwoRepository;

    public FormTwoViewModelFactory(FormTwoRepository formTwoRepository) {
        this.formTwoRepository = formTwoRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == FormTwoViewModel.class)
            return (T) new FormTwoViewModel(formTwoRepository);
        if (modelClass == LivelihoodViewModel.class)
            return (T) new LivelihoodViewModel(formTwoRepository);
        if (modelClass == MembersViewModel.class)
            return (T) new MembersViewModel(formTwoRepository);
        throw new RuntimeException("Check viewmodel dependant");
    }
}
