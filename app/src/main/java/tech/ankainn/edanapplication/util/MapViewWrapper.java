package tech.ankainn.edanapplication.util;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.android.gms.maps.MapView;

public class MapViewWrapper implements DefaultLifecycleObserver {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private MapView mapView;
    private Bundle savedInstanceState;

    public MapViewWrapper(MapView mapView, Bundle savedInstanceState, LifecycleOwner lifecycleOwner) {
        this.mapView = mapView;
        this.savedInstanceState = savedInstanceState;
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Bundle mapViewBundle = null;
        if(savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        mapView.onStart();
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        mapView.onResume();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        mapView.onPause();
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        mapView.onStop();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        mapView.onDestroy();
        mapView = null;
    }

    public void onLowMemory() {
        if(mapView != null) {
            mapView.onLowMemory();
        }
    }

    public MapView get() {
        return mapView;
    }
}
