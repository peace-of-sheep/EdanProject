package tech.ankainn.edanapplication.ui.formOne;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentFormHostBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.formTwo.BackDialogFragment;
import tech.ankainn.edanapplication.ui.formTwo.FormTwoHostFragmentDirections;
import tech.ankainn.edanapplication.ui.formTwo.HouseholdViewModel;
import tech.ankainn.edanapplication.ui.formTwo.MembersViewModel;
import tech.ankainn.edanapplication.ui.geninfo.GenInfViewModel;
import tech.ankainn.edanapplication.ui.geninfo.MapViewModel;

public class FormOneHostFragment extends BindingFragment<FragmentFormHostBinding> {

    private NavController navController;

    private OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            DialogFragment fragment = new BackDialogFragment();
            fragment.show(getParentFragmentManager(), "back");
        }
    };

    @Override
    protected FragmentFormHostBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentFormHostBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), backPressedCallback);

        binding().formHostFragmentContainer.post(() -> {
            navController = Navigation.findNavController(requireActivity(), R.id.form_host_fragment_container);
            navController.setGraph(R.navigation.form_one_graph);

            /*ViewModelProvider viewModelProvider = new ViewModelProvider(navController.getViewModelStoreOwner(R.id.form_two_graph));
            MapViewModel mapViewModel = viewModelProvider.get(MapViewModel.class);
            GenInfViewModel genInfViewModel = viewModelProvider.get(GenInfViewModel.class);
            HouseholdViewModel householdViewModel = viewModelProvider.get(HouseholdViewModel.class);
            MembersViewModel membersViewModel = viewModelProvider.get(MembersViewModel.class);*/

            binding().btnSave.setOnClickListener(v -> {
                /*viewModel.collectData(mapViewModel.requestData(),
                        genInfViewModel.requestData(),
                        householdViewModel.requestData(),
                        membersViewModel.requestData());*/
                Navigation.findNavController(requireActivity(), R.id.fragment_container)
                        .popBackStack();
            });

            binding().btnCamera.setOnClickListener(v ->
                    Navigation.findNavController(requireActivity(), R.id.fragment_container)
                            .navigate(FormOneHostFragmentDirections.actionFormOneToCamera()));

            binding().btnBack.setOnClickListener(v -> navController.navigate(R.id.action_back));
            binding().btnNext.setOnClickListener(v -> navController.navigate(R.id.action_next));
        });
    }
}
