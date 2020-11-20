package tech.ankainn.edanapplication.ui.formOne;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentFormOneHostBinding;
import tech.ankainn.edanapplication.global.AlertDialogFragment;
import tech.ankainn.edanapplication.global.Options;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.OwnerFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.view.NavigatorItem;

import static tech.ankainn.edanapplication.util.NavigationUtil.getChildNavController;
import static tech.ankainn.edanapplication.util.NavigationUtil.setupWithNavController;

public class FormOneHostFragment extends BindingFragment<FragmentFormOneHostBinding> implements OwnerFragment {

    private static final int[] destinationsId = {R.id.header_fragment, R.id.extra_fragment,
            R.id.map_fragment, R.id.location_fragment, R.id.damage_one_fragment,
            R.id.damage_two_fragment, R.id.damage_three_fragment, R.id.activities_fragment,
            R.id.needs_fragment};

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

        FormOneHostFragmentArgs args = FormOneHostFragmentArgs.fromBundle(requireArguments());
        long userId = args.getUserId();
        String username = args.getUsername();
        long tempId = args.getFormOneId();

        ViewModelProvider.Factory factory = InjectorUtil.provideFormOneViewModelFactory(requireContext());
        FormOneViewModel viewModel = new ViewModelProvider(this, factory).get(FormOneViewModel.class);

        viewModel.loadFormOne(tempId, userId, username);

        NavController parentNavController = NavHostFragment.findNavController(this);

        NavController childNavController = getChildNavController(getChildFragmentManager(), R.id.form_host_fragment_container);
        setupWithNavController(binding().navigator, childNavController);

        childNavController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.activities_others_fragment || destination.getId() == R.id.needs_others_fragment) {
                binding().navigator.showHideArrows(false);
            } else {
                binding().navigator.showHideArrows(true);
            }
        });

        binding().navigator.setDestinations(destinationsId);

        binding().navigator.addItemView(new NavigatorItem(R.drawable.ic_save_24, R.string.save, item -> {
                    viewModel.saveFormOne();
                    parentNavController.popBackStack();
        }));

        binding().navigator.addItemView(new NavigatorItem(R.drawable.ic_photo_camera_24, R.string.camera,
                item -> parentNavController.navigate(FormOneHostFragmentDirections.actionFormOneToCamera())));

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
