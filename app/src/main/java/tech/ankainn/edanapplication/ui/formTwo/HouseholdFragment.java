package tech.ankainn.edanapplication.ui.formTwo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentHouseholdBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;

import static tech.ankainn.edanapplication.util.NavigationUtil.getViewModelProvider;

public class HouseholdFragment extends BindingFragment<FragmentHouseholdBinding> {

    private FormTwoViewModel viewModel;

    @Override
    protected FragmentHouseholdBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHouseholdBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider viewModelProvider = getViewModelProvider(requireActivity(), R.id.fragment_container, R.id.form_two_host_graph);

        viewModel = viewModelProvider.get(FormTwoViewModel.class);

        viewModel.getHouseholdData().observe(getViewLifecycleOwner(),
                householdData -> binding().setHousehold(householdData));

        binding().textFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setIdFloor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding().textWall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setIdWall(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding().textRoof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setIdRoof(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
