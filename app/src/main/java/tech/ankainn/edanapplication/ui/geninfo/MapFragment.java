package tech.ankainn.edanapplication.ui.geninfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.binding.Converter;
import tech.ankainn.edanapplication.databinding.FragmentMapBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.MapViewWrapper;

import static tech.ankainn.edanapplication.util.NavigationUtil.getViewModelProvider;

public class MapFragment extends BindingFragment<FragmentMapBinding> {

    private MapViewWrapper mapViewWrapper;

    private MapViewModel viewModel;

    @Override
    protected FragmentMapBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMapBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapViewWrapper = new MapViewWrapper(binding().mapView, savedInstanceState, getViewLifecycleOwner());

        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);

        int form = MapFragmentArgs.fromBundle(requireArguments()).getForm();
        int graphId = form == 1 ? R.id.form_one_host_graph : R.id.form_two_host_graph;
        ViewModelProvider viewModelProvider = getViewModelProvider(navController, graphId);

        viewModel = viewModelProvider.get(MapViewModel.class);

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
