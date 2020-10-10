package tech.ankainn.edanapplication.ui.geninfo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.databinding.FragmentLocationBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class LocationFragment extends BindingFragment<FragmentLocationBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideGenInfViewModelFactory(requireContext());
        MapLocationViewModel viewModel = new ViewModelProvider(this, factory).get(MapLocationViewModel.class);

        viewModel.getMapLocationData().observe(getViewLifecycleOwner(),
                mapLocationData -> binding().setMapLocation(mapLocationData));
    }
}
