package tech.ankainn.edanapplication.form;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import org.jetbrains.annotations.NotNull;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentMapLocationBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.MapViewWrapper;
import timber.log.Timber;

public class MapLocationFragment extends BaseFragment implements OnMapReadyCallback {

    private AutoClearedValue<FragmentMapLocationBinding> binding;
    private MapViewWrapper mapViewWrapper;

    private SharedCommonFormViewModel sharedViewModel;
    private MapLocationViewModel viewModel;

    @NotNull
    @Override
    protected View getViewBinding(LayoutInflater inflater, ViewGroup container) {
        FragmentMapLocationBinding bindingTemp =
                DataBindingUtil.inflate(inflater, R.layout.fragment_map_location, container, false);
        binding = new AutoClearedValue<>(bindingTemp);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedCommonFormViewModel.class);
        viewModel = new ViewModelProvider(this).get(MapLocationViewModel.class);

        setupHideSoftKeyboard(binding.get().getRoot());

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
