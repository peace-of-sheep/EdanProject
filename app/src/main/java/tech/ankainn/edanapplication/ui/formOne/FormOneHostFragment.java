package tech.ankainn.edanapplication.ui.formOne;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentFormOneHostBinding;
import tech.ankainn.edanapplication.global.AlertDialogFragment;
import tech.ankainn.edanapplication.global.Options;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.OwnerFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.NavigationUI2;

import static tech.ankainn.edanapplication.util.NavigationUtil.getChildNavController;

public class FormOneHostFragment extends BindingFragment<FragmentFormOneHostBinding> implements OwnerFragment {

    private OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            new AlertDialogFragment.Builder("back")
                    .setMessage(R.string.back_quit)
                    .build(getParentFragmentManager());
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavController parentNavController = NavHostFragment.findNavController(this);

        ViewModelStoreOwner owner = parentNavController.getBackStackEntry(getDestinationId());
        ViewModelProvider.Factory factory = InjectorUtil.provideFormOneViewModelFactory(requireContext());
        FormOneViewModel viewModel = new ViewModelProvider(owner, factory).get(FormOneViewModel.class);

        long tempId = FormOneHostFragmentArgs.fromBundle(requireArguments()).getFormOneId();
        viewModel.setFormOneId(tempId);

        binding().btnSave.setOnClickListener(v -> {
            viewModel.saveFormOne();
            parentNavController.popBackStack();
        });

        NavController childNavController = getChildNavController(getChildFragmentManager(), R.id.form_host_fragment_container);
        final int[] destinations = {R.id.map_fragment, R.id.location_fragment, R.id.gen_inf_fragment,
                R.id.damage_one_fragment, R.id.damage_two_fragment, R.id.damage_three_fragment,
                R.id.activities_fragment, R.id.needs_fragment};
        NavigationUI2.setupWithNavController(binding().btnNext, binding().btnBack, destinations, childNavController);

        Options.getInstance().observe(getViewLifecycleOwner(), (emitter, option) -> {
            if(emitter.equals("back") && option == 0) {
                viewModel.clearFormOne();
                parentNavController.popBackStack();
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), backPressedCallback);
    }

    @Override
    public int getDestinationId() {
        return R.id.form_one_host_fragment;
    }
}
