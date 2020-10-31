package tech.ankainn.edanapplication.ui.geninfo;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.danger.DangerEntity;
import tech.ankainn.edanapplication.databinding.FragmentHeaderBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class HeaderFragment extends BindingFragment<FragmentHeaderBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideGenInfViewModelFactory(requireContext());
        HeaderViewModel viewModel = new ViewModelProvider(this, factory).get(HeaderViewModel.class);

        viewModel.getHeaderData().observe(getViewLifecycleOwner(),
                headerData -> binding().setHeaderData(headerData));

        viewModel.getNameDangerGroup().observe(getViewLifecycleOwner(), name -> binding().textDangerGroup.setText(name));
        viewModel.getListDangers().observe(getViewLifecycleOwner(), dangers -> {
            binding().setListDangers(dangers);
        });
        binding().textDangerType.setOnItemClickListener((p, v, pos, id) -> {
            DangerEntity danger = (DangerEntity) binding().textDangerType.getAdapter().getItem(pos);
            viewModel.onDanger(danger);
        });

        viewModel.getDptosNames().observe(getViewLifecycleOwner(), names -> binding().setDepartmentNames(names));
        viewModel.getProvNames().observe(getViewLifecycleOwner(), names -> binding().setProvinceNames(names));
        viewModel.getDistNames().observe(getViewLifecycleOwner(), names -> binding().setDistrictNames(names));

        binding().textDepartment.setOnItemClickListener((p, v, pos, id) -> {
            binding().textProvince.setText(null);
            binding().textDistrict.setText(null);
            viewModel.onDepartmentPos(pos);
        });
        binding().textProvince.setOnItemClickListener((p, v, pos, id) -> {
            binding().textDistrict.setText(null);
            viewModel.onProvincePos(pos);
        });
        binding().textDistrict.setOnItemClickListener((p, v, pos, id) -> viewModel.onDistrictPos(pos));
    }
}
