package tech.ankainn.edanapplication.ui.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.ui.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentMapLocationBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.MapViewWrapper;

public class MapLocationFragment extends BaseFragment implements OnMapReadyCallback {

    private AutoClearedValue<FragmentMapLocationBinding> binding;
    private MapViewWrapper mapViewWrapper;

    private CommonFormViewModel sharedViewModel;
    private MapLocationViewModel viewModel;

    @NotNull
    @Override
    protected View makeView(LayoutInflater inflater, ViewGroup container) {
        FragmentMapLocationBinding bindingTemp =
                FragmentMapLocationBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(bindingTemp);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(CommonFormViewModel.class);
        viewModel = new ViewModelProvider(this).get(MapLocationViewModel.class);

        mapViewWrapper = new MapViewWrapper(binding.get().mapView, savedInstanceState);
        getViewLifecycleOwner().getLifecycle().addObserver(mapViewWrapper);

        binding.get().mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setTrafficEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setTiltGesturesEnabled(false);
        binding.get().mapPicker.setVisibility(View.VISIBLE);

        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.google_map_logo_padding);
        map.setPadding(0, 0, 0, bottomPadding);

        LatLng currentLagLng = viewModel.getCurrentLatLng();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLagLng, 4.5f));

        map.setOnCameraIdleListener(() -> {
            LatLng center = map.getCameraPosition().target;
            binding.get().setLatLng(center);
        });
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewWrapper.onLowMemory();
    }
}
