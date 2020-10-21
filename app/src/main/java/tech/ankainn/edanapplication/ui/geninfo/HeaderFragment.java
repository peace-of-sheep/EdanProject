package tech.ankainn.edanapplication.ui.geninfo;

import android.os.Bundle;
import android.text.Editable;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.databinding.FragmentHeaderBinding;
import tech.ankainn.edanapplication.global.Picker;
import tech.ankainn.edanapplication.global.PickerFragment;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.OnAfterTextChanged;
import tech.ankainn.edanapplication.util.TextInputLayoutUtil;

public class HeaderFragment extends BindingFragment<FragmentHeaderBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideGenInfViewModelFactory(requireContext());
        HeaderViewModel viewModel = new ViewModelProvider(this, factory).get(HeaderViewModel.class);

        TextInputLayoutUtil.setOnClickListener(binding().layoutDate,
                v -> PickerFragment.showPicker(PickerFragment.MODE_DATE, this));
        TextInputLayoutUtil.setOnClickListener(binding().layoutHour,
                v -> PickerFragment.showPicker(PickerFragment.MODE_TIME, this));

        Picker.getInstance().observe(getViewLifecycleOwner(), (emitter, value) -> {
            if ("date".equals(emitter)) {
                binding().textDate.setText(value);
            } else if ("hour".equals(emitter)) {
                binding().textHour.setText(value);
            }
        });

        binding().textDangerGroup.setOnItemClickListener((parent, view, position, id) -> {
            binding().textDangerType.setText(null);
            viewModel.onDangerGroupPos(position);
        });
        binding().textDangerType.setOnItemClickListener((p, v, pos, id) ->
                viewModel.onDangerTypePos(pos));

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

        viewModel.getHeaderData().observe(getViewLifecycleOwner(),
                headerData -> binding().setHeaderData(headerData));

        viewModel.getDangerGroupText().observe(getViewLifecycleOwner(),
                names -> binding().setDangerGroupNames(names));
        viewModel.getDangerTypeText().observe(getViewLifecycleOwner(),
                names -> binding().setDangerTypeNames(names));

        viewModel.getDptosNames().observe(getViewLifecycleOwner(), names -> binding().setDepartmentNames(names));
        viewModel.getProvNames().observe(getViewLifecycleOwner(), names -> binding().setProvinceNames(names));
        viewModel.getDistNames().observe(getViewLifecycleOwner(), names -> binding().setDistrictNames(names));
    }
}
