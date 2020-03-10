package tech.ankainn.edanapplication.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentMapLocationBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.MapViewHolder;

public class MapLocationFragment extends Fragment implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private AutoClearedValue<FragmentMapLocationBinding> binding;
    private MapViewHolder mapViewHolder;

    private SharedCommonFormViewModel sharedViewModel;
    private MapLocationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMapLocationBinding binding = FragmentMapLocationBinding.inflate(inflater, container, false);
        this.binding = new AutoClearedValue<>(binding);
        getViewLifecycleOwner().getLifecycle().addObserver(this.binding);
        binding.getRoot().setClickable(true);
        binding.getRoot().setFocusableInTouchMode(true);
        binding.getRoot().setFitsSystemWindows(false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedCommonFormViewModel.class);
        viewModel = new ViewModelProvider(this).get(MapLocationViewModel.class);

        initMapViewBundle(savedInstanceState);
        binding.get().mapView.getMapAsync(this);
    }

    private void initMapViewBundle(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if(savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapViewHolder = new MapViewHolder(binding.get().mapView, mapViewBundle);
        getViewLifecycleOwner().getLifecycle().addObserver(mapViewHolder);
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
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLagLng, 5.5f));

        map.setOnCameraIdleListener(() -> {
            LatLng center = map.getCameraPosition().target;
            viewModel.setCurrentLatLng(center);
            sharedViewModel.setLocation(center);
        });
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mapViewHolder.get() != null) {
            mapViewHolder.get().onLowMemory();
        }
    }
}
