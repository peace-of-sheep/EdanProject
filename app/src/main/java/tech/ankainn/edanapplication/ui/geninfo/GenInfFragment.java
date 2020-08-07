package tech.ankainn.edanapplication.ui.geninfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentGenInfBinding;
import tech.ankainn.edanapplication.global.Picker;
import tech.ankainn.edanapplication.global.PickerFragment;
import tech.ankainn.edanapplication.ui.common.BindingFragment;

import static tech.ankainn.edanapplication.global.PickerFragment.MODE_DATE;
import static tech.ankainn.edanapplication.global.PickerFragment.MODE_TIME;
import static tech.ankainn.edanapplication.util.NavigationUtil.getViewModelProvider;

public class GenInfFragment extends BindingFragment<FragmentGenInfBinding> {

    private GenInfViewModel viewModel;

    @Override
    protected FragmentGenInfBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentGenInfBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int form = GenInfFragmentArgs.fromBundle(requireArguments()).getForm();
        int destinationId = form == 1 ? R.id.form_one_host_fragment : R.id.form_two_host_fragment;

        NavBackStackEntry owner = Navigation
                .findNavController(requireActivity(), R.id.fragment_container)
                .getBackStackEntry(destinationId);
        viewModel = new ViewModelProvider(owner).get(GenInfViewModel.class);

        viewModel.getGenInfData().observe(getViewLifecycleOwner(),
                genInfData -> binding().setGenInfo(genInfData));

        binding().textDate.setOnClickListener(v -> PickerFragment.showPicker(MODE_DATE, this));
        binding().textHour.setOnClickListener(v -> PickerFragment.showPicker(MODE_TIME, this));

        Picker.getInstance().observe(getViewLifecycleOwner(), (emitter, value) -> {
            switch (emitter) {
                case "date":
                    binding().textDate.setText(value);
                    break;
                case "hour":
                    binding().textHour.setText(value);
                    break;
            }
        });
    }
}
