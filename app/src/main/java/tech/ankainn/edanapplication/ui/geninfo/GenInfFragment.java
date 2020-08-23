package tech.ankainn.edanapplication.ui.geninfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentGenInfBinding;
import tech.ankainn.edanapplication.global.Picker;
import tech.ankainn.edanapplication.global.PickerFragment;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.viewmodel.GenInfViewModelFactory;

import static tech.ankainn.edanapplication.global.PickerFragment.MODE_DATE;
import static tech.ankainn.edanapplication.global.PickerFragment.MODE_TIME;

public class GenInfFragment extends BindingFragment<FragmentGenInfBinding> {

    private GenInfViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelStoreOwner owner = ScopeNavHostFragment.getOwner(this);
        GenInfViewModelFactory factory = InjectorUtil.provideGenInfViewModelFactory();
        viewModel = new ViewModelProvider(owner, factory).get(GenInfViewModel.class);

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
