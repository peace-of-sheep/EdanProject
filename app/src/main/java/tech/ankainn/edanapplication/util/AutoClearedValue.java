package tech.ankainn.edanapplication.util;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import timber.log.Timber;

public class AutoClearedValue<T> implements LifecycleObserver {

    private T value;

    public AutoClearedValue(T value) {
        this.value = value;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clearValue() {
        Timber.i("clearValue:");
        value = null;
    }

    public T get() {
        return value;
    }
}
