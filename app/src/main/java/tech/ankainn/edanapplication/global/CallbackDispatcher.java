package tech.ankainn.edanapplication.global;

import androidx.lifecycle.LifecycleOwner;

public abstract class CallbackDispatcher<T> {

    private CallbackWrapper<T> callbackWrapper = new CallbackWrapper<>();

    public void observe(LifecycleOwner owner, T callback) {
        callbackWrapper.observe(owner, callback);
    }

    protected void dispatch(CallbackWrapper.Iterator<T> iterator) {
        callbackWrapper.dispatch(iterator);
    }
}
