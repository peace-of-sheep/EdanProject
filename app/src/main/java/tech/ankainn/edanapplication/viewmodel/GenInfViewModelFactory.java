package tech.ankainn.edanapplication.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.repositories.GenInfRepository;
import tech.ankainn.edanapplication.repositories.UbigeoRepository;
import tech.ankainn.edanapplication.ui.geninfo.ExtraViewModel;
import tech.ankainn.edanapplication.ui.geninfo.HeaderViewModel;
import tech.ankainn.edanapplication.ui.geninfo.MapLocationViewModel;

public class GenInfViewModelFactory implements ViewModelProvider.Factory {

    private GenInfRepository genInfRepository;
    private UbigeoRepository ubigeoDangerRepository;

    public GenInfViewModelFactory(GenInfRepository genInfRepository, UbigeoRepository ubigeoDangerRepository) {
        this.genInfRepository = genInfRepository;
        this.ubigeoDangerRepository = ubigeoDangerRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == HeaderViewModel.class)
            return (T) new HeaderViewModel(genInfRepository, ubigeoDangerRepository);
        if (modelClass == ExtraViewModel.class)
            return (T) new ExtraViewModel(genInfRepository);
        if (modelClass == MapLocationViewModel.class)
            return (T) new MapLocationViewModel(genInfRepository);
        throw new RuntimeException("Don't use this factory");
    }
}
