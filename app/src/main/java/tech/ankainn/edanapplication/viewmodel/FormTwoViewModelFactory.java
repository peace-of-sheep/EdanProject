package tech.ankainn.edanapplication.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.repositories.LivelihoodRepository;
import tech.ankainn.edanapplication.repositories.MemberRepository;
import tech.ankainn.edanapplication.repositories.ReniecRepository;
import tech.ankainn.edanapplication.ui.formTwoA.FormTwoViewModel;
import tech.ankainn.edanapplication.ui.formTwoA.MemberViewModel;
import tech.ankainn.edanapplication.ui.formTwoB.LivelihoodViewModel;

public class FormTwoViewModelFactory implements ViewModelProvider.Factory {

    private FormTwoRepository formTwoRepository;
    private MemberRepository memberRepository;
    private LivelihoodRepository livelihoodRepository;
    private ReniecRepository reniecRepository;

    public FormTwoViewModelFactory(FormTwoRepository formTwoRepository,
                                   MemberRepository memberRepository,
                                   LivelihoodRepository livelihoodRepository,
                                   ReniecRepository reniecRepository) {
        this.formTwoRepository = formTwoRepository;

        this.memberRepository = memberRepository;
        this.livelihoodRepository = livelihoodRepository;
        this.reniecRepository = reniecRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == FormTwoViewModel.class)
            return (T) new FormTwoViewModel(formTwoRepository);
        if (modelClass == MemberViewModel.class)
            return (T) new MemberViewModel(memberRepository, reniecRepository);
        if (modelClass == LivelihoodViewModel.class)
            return (T) new LivelihoodViewModel(livelihoodRepository, memberRepository);
        throw new RuntimeException("Don't use this factory");
    }
}
