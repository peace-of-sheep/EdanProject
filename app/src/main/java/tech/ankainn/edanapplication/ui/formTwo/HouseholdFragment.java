package tech.ankainn.edanapplication.ui.formTwo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentHouseholdBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;

public class HouseholdFragment extends BindingFragment<FragmentHouseholdBinding> {

    private FormTwoViewModel viewModel;

    @Override
    protected FragmentHouseholdBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHouseholdBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavBackStackEntry owner = Navigation
                .findNavController(requireActivity(), R.id.fragment_container)
                .getBackStackEntry(R.id.form_two_host_fragment);
        viewModel = new ViewModelProvider(owner).get(FormTwoViewModel.class);

        viewModel.getHouseholdData().observe(getViewLifecycleOwner(),
                householdData -> binding().setHousehold(householdData));
    }
}
