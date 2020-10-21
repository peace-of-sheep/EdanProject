package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentFormTwoHostBinding;
import tech.ankainn.edanapplication.global.AlertDialogFragment;
import tech.ankainn.edanapplication.global.Options;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.OwnerFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.view.NavigatorItem;

import static tech.ankainn.edanapplication.util.NavigationUtil.getChildNavController;
import static tech.ankainn.edanapplication.util.NavigationUtil.setupWithNavController;

public class FormTwoHostFragment extends BindingFragment<FragmentFormTwoHostBinding> implements OwnerFragment {

    private static final int[] destinationsId = {R.id.header_fragment, R.id.extra_fragment,
            R.id.map_fragment, R.id.household_fragment, R.id.list_member_fragment};

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

        long tempId = FormTwoHostFragmentArgs.fromBundle(requireArguments()).getFormTwoId();

        ViewModelProvider.Factory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        viewModel = new ViewModelProvider(this, factory).get(FormTwoViewModel.class);

        viewModel.setFormTwoId(tempId);

        NavController parentNavController = NavHostFragment.findNavController(this);

        NavController childNavController = getChildNavController(getChildFragmentManager(), R.id.form_host_fragment_container);
        setupWithNavController(binding().navigator, childNavController);

        childNavController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            binding().navigator.showHideArrows(destination.getId() != R.id.member_fragment &&
                    destination.getId() != R.id.livelihood_list_fragment);
        });

        binding().navigator.setDestinations(destinationsId);
        binding().navigator.addItemView(new NavigatorItem(R.drawable.ic_save_24, R.string.save, item -> {
            viewModel.saveFormTwo();
            parentNavController.popBackStack();
        }));

        binding().navigator.addItemView(new NavigatorItem(R.drawable.ic_photo_camera_24, R.string.camera,
                item -> parentNavController.navigate(FormTwoHostFragmentDirections.actionFormTwoToCamera())));

        binding().navigator.addItemView(new NavigatorItem(R.drawable.ic_folder_24dp, R.string.short_form_two_b,
                item -> {
                    if (childNavController.getCurrentDestination().getId() != R.id.livelihood_list_fragment) {
                        binding().navigator.showHideArrows(false);
                        item.setLabel(R.string.short_form_two_a);
                        childNavController.navigate(R.id.livelihood_list_fragment);
                    } else {
                        binding().navigator.showHideArrows(true);
                        item.setLabel(R.string.short_form_two_b);
                        childNavController.popBackStack();
                    }
        }));

        Options.getInstance().observe(getViewLifecycleOwner(), (emitter, option) -> {
            if (emitter.equals("back") && option == 0) {
                viewModel.clearFormTwo();
                parentNavController.popBackStack();
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), backPressedCallback);
    }

    @Override
    public int getDestinationId() {
        return R.id.form_two_host_fragment;
    }
}
