package tech.ankainn.edanapplication.ui.geninfo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import tech.ankainn.edanapplication.databinding.FragmentHeaderBinding;
import tech.ankainn.edanapplication.global.Picker;
import tech.ankainn.edanapplication.global.PickerFragment;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.Tagger;
import tech.ankainn.edanapplication.util.TextInputLayoutUtil;
import timber.log.Timber;

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
            viewModel.setDangerGroup(requireContext(), position);
        });
        binding().textDangerType.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setDanger(requireContext(), pos);
        });
        binding().textDepartment.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setDepartment(requireContext(), pos);
        });
        binding().textProvince.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setProvince(requireContext(), pos);
        });
        binding().textDistrict.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setDistrict(requireContext(), pos);
        });

        viewModel.getHeaderData().observe(getViewLifecycleOwner(),
                headerData -> binding().setHeaderData(headerData));
    }
}
