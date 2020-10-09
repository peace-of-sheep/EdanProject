package tech.ankainn.edanapplication.ui.geninfo;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Locale;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.binding.Converter;
import tech.ankainn.edanapplication.databinding.FragmentMapBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class MapFragment extends BindingFragment<FragmentMapBinding> {

    private MapViewWrapper mapViewWrapper;

    private MapLocationViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding().getRoot().post(() -> {
            mapViewWrapper = new MapViewWrapper(binding().mapView, savedInstanceState, getViewLifecycleOwner());
            binding().mapView.getMapAsync(this::onMapCallback);
        });

        ViewModelProvider.Factory factory = InjectorUtil.provideViewModelFactory(requireContext());
        viewModel = new ViewModelProvider(this, factory).get(MapLocationViewModel.class);

        viewModel.getMapLocationData().observe(getViewLifecycleOwner(),
                mapLocationData -> binding().setMapLocation(mapLocationData));

        /*viewModel.getState().observe(getViewLifecycleOwner(), state -> {
            if (state == MapLocationViewModel.State.LOADING) {
                binding().btnLocation.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_forward_24));
            } else {
                binding().btnLocation.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_my_location_24));
            }
        });

        binding().btnLocation.setOnClickListener(v -> getLastLocation(requireContext()));*/
    }

    private void onMapCallback(GoogleMap map) {
        map.setTrafficEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setTiltGesturesEnabled(false);

        map.setOnCameraIdleListener(() -> {
            LatLng center = map.getCameraPosition().target;
            binding().textLatitude.setText(Converter.doubleToString(center.latitude));
            binding().textLongitude.setText(Converter.doubleToString(center.longitude));
        });

        viewModel.getMapLocationData().observe(getViewLifecycleOwner(), mapLocationData -> {
            LatLng latLng = new LatLng(mapLocationData.latitude, mapLocationData.longitude);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5.5f));
        });
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewWrapper.onLowMemory();
    }

    private void getLastLocation(Context context) {
        Dexter.withContext(context)
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        viewModel.searchCurrentLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(requireContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }
}
