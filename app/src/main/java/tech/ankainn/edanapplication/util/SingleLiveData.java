package tech.ankainn.edanapplication.util;

import androidx.annotation.MainThread;
import androidx.collection.ArraySet;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.Iterator;

import retrofit2.internal.EverythingIsNonNull;

public class SingleLiveData<T> extends MediatorLiveData<T> {

    private ArraySet<ObserverWrapper<? super T>> observers = new ArraySet<>();

    @MainThread
    @Override
    @EverythingIsNonNull
    public void observe(LifecycleOwner owner, Observer<? super T> observer) {
        ObserverWrapper<? super T> wrapper = new ObserverWrapper<>(observer);
        observers.add(wrapper);
        super.observe(owner, wrapper);
    }

    @MainThread
    @Override
    @EverythingIsNonNull
    public void observeForever(Observer<? super T> observer) {
        ObserverWrapper<? super T> wrapper = new ObserverWrapper<>(observer);
        observers.add(wrapper);
        super.observeForever(wrapper);
    }

    @MainThread
    @Override
    @EverythingIsNonNull
    public void removeObserver(Observer<? super T> observer) {
        if (observers.remove(observer)) {
            super.removeObserver(observer);
            return;
        }
        Iterator<ObserverWrapper<? super T>> iterator = observers.iterator();
        while (iterator.hasNext()) {
            ObserverWrapper<? super T> wrapper = iterator.next();
            if (wrapper.observer == observer) {
                iterator.remove();
                super.removeObserver(wrapper);
                break;
            }
        }
    }

    @MainThread
    @Override
    public void setValue(T value) {
        Iterator<ObserverWrapper<? super T>> iterator = observers.iterator();
        while (iterator.hasNext()) {
            ObserverWrapper<? super T> wrapper = iterator.next();
            wrapper.newValue();
        }
        super.setValue(value);
    }

    private static class ObserverWrapper<K> implements Observer<K> {

        private Observer<K> observer;
        private boolean pending = false;

        public ObserverWrapper(Observer<K> observer) {
            this.observer = observer;
        }

        @Override
        public void onChanged(K k) {
            if (pending) {
                pending = false;
                observer.onChanged(k);
            }
        }

        public void newValue() {
            pending = true;
        }
    }
}


