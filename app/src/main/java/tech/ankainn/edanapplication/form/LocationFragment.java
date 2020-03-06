package tech.ankainn.edanapplication.form;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentLocationBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.MapViewHolder;
import timber.log.Timber;

public class LocationFragment extends BaseFragment implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private SharedCommonFormViewModel sharedViewModel;

    private AutoClearedValue<FragmentLocationBinding> binding;

    private MapViewHolder mapViewHolder;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_location;
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        this.binding = new AutoClearedValue<>((FragmentLocationBinding) binding);
        getViewLifecycleOwner().getLifecycle().addObserver(this.binding);
    }

    @Override
    protected boolean shouldFitsSystemWindows() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedCommonFormViewModel.class);

        initMapViewBundle(savedInstanceState);
        mapViewHolder.get().getMapAsync(this);

        sharedViewModel.setFullscreen(true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        sharedViewModel.setFullscreen(!hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(!requireActivity().isChangingConfigurations()) {
            sharedViewModel.setFullscreen(false);
        }
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
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewHolder.get().onLowMemory();
    }
}
