package tech.ankainn.edanapplication.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class AutoClearedValue<T> implements DefaultLifecycleObserver {

    private T value;

    public AutoClearedValue(T value, LifecycleOwner owner) {
        this.value = value;
        owner.getLifecycle().addObserver(this);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        value = null;
    }

    public T get() {
        return value;
    }
}
