package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.binding.BindingAdapters;
import tech.ankainn.edanapplication.databinding.FragmentFormTwoHostBinding;
import tech.ankainn.edanapplication.global.AlertDialogFragment;
import tech.ankainn.edanapplication.global.Options;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.OwnerFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.NavigationUI2;
import tech.ankainn.edanapplication.viewmodel.FormTwoViewModelFactory;

import static tech.ankainn.edanapplication.util.NavigationUtil.getChildNavController;

public class FormTwoHostFragment extends BindingFragment<FragmentFormTwoHostBinding> implements OwnerFragment {

    private FormTwoViewModel viewModel;

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
        FormTwoViewModelFactory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        viewModel = new ViewModelProvider(owner, factory).get(FormTwoViewModel.class);

        long tempId = FormTwoHostFragmentArgs.fromBundle(requireArguments()).getFormTwoId();
        viewModel.setFormTwoId(tempId);

        binding().btnSave.setOnClickListener(v -> {
            viewModel.saveFormTwo();
            parentNavController.popBackStack();
        });
        binding().btnCamera.setOnClickListener(v ->
                parentNavController.navigate(FormTwoHostFragmentDirections.actionFormTwoToCamera()));

        Options.getInstance().observe(getViewLifecycleOwner(), (emitter, option) -> {
            if (emitter.equals("back") && option == 0) {
                viewModel.clearFormTwo();
                parentNavController.popBackStack();
            }
        });

        NavController childNavController = getChildNavController(getChildFragmentManager(), R.id.form_host_fragment_container);
        final int[] destinations = {R.id.map_fragment, R.id.gen_inf_fragment, R.id.household_fragment, R.id.members_fragment};
        NavigationUI2.setupWithNavController(binding().btnNext, binding().btnBack, destinations, childNavController);

        binding().btnFormTwoSwitch.setOnClickListener(v -> {
            if (childNavController.getCurrentDestination().getId() != R.id.livelihood_fragment) {
                BindingAdapters.showHide(binding().btnNext, false);
                BindingAdapters.showHide(binding().btnBack, false);
                childNavController.navigate(R.id.livelihood_fragment);
            } else {
                BindingAdapters.showHide(binding().btnNext, true);
                BindingAdapters.showHide(binding().btnBack, true);
                childNavController.popBackStack();
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), backPressedCallback);
    }

    @Override
    public int getDestinationId() {
        return R.id.form_two_host_fragment;
    }
}
