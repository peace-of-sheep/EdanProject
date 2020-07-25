package tech.ankainn.edanapplication.ui.formTwo;

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

import static tech.ankainn.edanapplication.util.NavigationUtil.getNavController;
import static tech.ankainn.edanapplication.util.NavigationUtil.getViewModelProvider;

public class FormTwoHostFragment extends BindingFragment<FragmentFormHostBinding> {

    private FormTwoViewModel viewModel;

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

        NavController parentNavController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
        ViewModelProvider viewModelProvider = getViewModelProvider(parentNavController, R.id.form_two_host_graph);

        viewModel = viewModelProvider.get(FormTwoViewModel.class);

        NavController navController = getNavController(getChildFragmentManager(), R.id.form_host_fragment_container);
        navController.setGraph(R.navigation.form_two_graph);

        binding().btnSave.setOnClickListener(v -> {
            viewModel.saveFile();
            parentNavController.popBackStack();
        });

        binding().btnCamera.setOnClickListener(v ->
                parentNavController.navigate(FormTwoHostFragmentDirections.actionFormTwoToCamera()));

        binding().btnBack.setOnClickListener(v -> navController.navigate(R.id.action_back));
        binding().btnNext.setOnClickListener(v -> navController.navigate(R.id.action_next));

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), backPressedCallback);
    }
}
