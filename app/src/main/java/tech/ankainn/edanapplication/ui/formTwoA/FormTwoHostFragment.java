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
import tech.ankainn.edanapplication.view.NavigatorItem;

import static tech.ankainn.edanapplication.util.NavigationUtil.getChildNavController;

public class FormTwoHostFragment extends BindingFragment<FragmentFormTwoHostBinding> implements OwnerFragment {

    private static final int[] destinationsId = {R.id.header_fragment, R.id.extra_fragment,
            R.id.map_fragment, R.id.household_fragment, R.id.members_fragment};

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

        ViewModelProvider.Factory factory = InjectorUtil.provideViewModelFactory(requireContext());
        viewModel = new ViewModelProvider(this, factory).get(FormTwoViewModel.class);

        NavController parentNavController = NavHostFragment.findNavController(this);

        NavController childNavController = getChildNavController(getChildFragmentManager(), R.id.form_host_fragment_container);
        NavigationUI2.setupWithNavController(binding().navigator, childNavController);

        long tempId = FormTwoHostFragmentArgs.fromBundle(requireArguments()).getFormTwoId();
        viewModel.setFormTwoId(tempId);

        binding().navigator.setDestinations(destinationsId);
        binding().navigator.addItemView(new NavigatorItem(R.drawable.ic_save_24, R.string.save, item -> {
            viewModel.saveFormTwo();
            parentNavController.popBackStack();
        }));

        /*binding().navigator.addItemView(new NavigatorItem(R.drawable.ic_photo_camera_24, R.string.camera,
                item -> parentNavController.navigate(FormTwoHostFragmentDirections.actionFormTwoToCamera())));*/

        /*binding().navigator.addItemView(new NavigatorItem(R.drawable.ic_folder_24dp, R.string.short_form_two_b,
                item -> {
                    if (childNavController.getCurrentDestination().getId() != R.id.livelihood_fragment) {
                        binding().navigator.showHideArrows(false);
                        item.setLabel(R.string.short_form_two_a);
                        childNavController.navigate(R.id.livelihood_fragment);
                    } else {
                        binding().navigator.showHideArrows(true);
                        item.setLabel(R.string.short_form_two_b);
                        childNavController.popBackStack();
                    }
        }));*/

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), backPressedCallback);

        Options.getInstance().observe(getViewLifecycleOwner(), (emitter, option) -> {
            if (emitter.equals("back") && option == 0) {
                viewModel.clearFormTwo();
                parentNavController.popBackStack();
            }
        });
    }

    @Override
    public int getDestinationId() {
        return R.id.form_two_host_fragment;
    }
}
