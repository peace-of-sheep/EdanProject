package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.databinding.FragmentHouseholdBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class HouseholdFragment extends BindingFragment<FragmentHouseholdBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        FormTwoViewModel viewModel = new ViewModelProvider(this, factory).get(FormTwoViewModel.class);

        binding().textUseHousehold.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setHouseholdUse(requireContext(), pos);
        });
        binding().textCondition.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setHouseholdCondition(requireContext(), pos);
        });
        binding().textRoof.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setTypeRoof(requireContext(), pos);
        });
        binding().textWall.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setTypeWall(requireContext(), pos);
        });
        binding().textFloor.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setTypeFloor(requireContext(), pos);
        });

        viewModel.getHouseholdData().observe(getViewLifecycleOwner(),
                householdData -> binding().setHousehold(householdData));
    }
}
