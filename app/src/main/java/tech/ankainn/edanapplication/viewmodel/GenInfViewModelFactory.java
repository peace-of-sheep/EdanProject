package tech.ankainn.edanapplication.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.repositories.GenInfRepository;
import tech.ankainn.edanapplication.ui.geninfo.GenInfViewModel;

public class GenInfViewModelFactory implements ViewModelProvider.Factory {

    private GenInfRepository genInfRepository;

    public GenInfViewModelFactory(GenInfRepository genInfRepository) {
        this.genInfRepository = genInfRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (GenInfViewModel.class == modelClass) {
            return (T) new GenInfViewModel(genInfRepository);
        }
        throw new RuntimeException("Don't use this factory");
    }
}
