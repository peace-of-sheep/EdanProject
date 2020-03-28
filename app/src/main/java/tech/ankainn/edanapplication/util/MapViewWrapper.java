package tech.ankainn.edanapplication.util;

import android.os.Bundle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.android.gms.maps.MapView;

public class MapViewWrapper implements LifecycleObserver {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private MapView mapView;

    public MapViewWrapper(MapView mapView, Bundle savedInstanceState) {
        this.mapView = mapView;

        Bundle mapViewBundle = null;
        if(savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void start() {
        mapView.onStart();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void resume() {
        mapView.onResume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void pause() {
        mapView.onPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void stop() {
        mapView.onStop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void destroy() {
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
