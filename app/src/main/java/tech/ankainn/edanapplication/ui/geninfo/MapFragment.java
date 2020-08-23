package tech.ankainn.edanapplication.ui.geninfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.binding.Converter;
import tech.ankainn.edanapplication.databinding.FragmentMapBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.viewmodel.GenInfViewModelFactory;

public class MapFragment extends BindingFragment<FragmentMapBinding> {

    private MapViewWrapper mapViewWrapper;

    private GenInfViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelStoreOwner owner = ScopeNavHostFragment.getOwner(this);
        GenInfViewModelFactory factory = InjectorUtil.provideGenInfViewModelFactory();
        viewModel = new ViewModelProvider(owner, factory).get(GenInfViewModel.class);

        mapViewWrapper = new MapViewWrapper(binding().mapView, savedInstanceState, getViewLifecycleOwner());

        binding().getRoot().post(() -> binding().mapView.getMapAsync(this::onMapCallback));
    }

    public void onMapCallback(GoogleMap map) {
        map.setTrafficEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setTiltGesturesEnabled(false);

        map.setOnCameraIdleListener(() -> {
            LatLng center = map.getCameraPosition().target;
            binding().textLatitude.setText(Converter.doubleToString(center.latitude));
            binding().textLongitude.setText(Converter.doubleToString(center.longitude));
        });

        viewModel.getMapLocationData().observe(getViewLifecycleOwner(), mapLocationData -> {
            binding().setMapLocation(mapLocationData);
            LatLng latLng = new LatLng(mapLocationData.latitude, mapLocationData.longitude);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5.5f));
        });
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewWrapper.onLowMemory();
    }
}
