package tech.ankainn.edanapplication.util;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.Iterator;

public class LiveEvent<T> extends MediatorLiveData<T> {

    private ArraySet<ObserverWrapper<? super T>> observers = new ArraySet<>();

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        ObserverWrapper<? super T> wrapper = new ObserverWrapper<>(observer);
        observers.add(wrapper);
        super.observe(owner, wrapper);
    }

    @Override
    public void observeForever(@NonNull Observer<? super T> observer) {
        ObserverWrapper<? super T> wrapper = new ObserverWrapper<>(observer);
        observers.add(wrapper);
        super.observeForever(wrapper);
    }

    @Override
    public void removeObserver(@NonNull Observer<? super T> observer) {
        if(observers.remove(observer)) {
            super.removeObserver(observer);
            return;
        }

        Iterator<ObserverWrapper<? super T>> iterator = observers.iterator();
        while (iterator.hasNext()) {
            ObserverWrapper<? super T> wrapper = iterator.next();
            if(wrapper.observer == observer) {
                iterator.remove();
                super.removeObserver(wrapper);
                break;
            }
        }
    }

    @Override
    public void setValue(T value) {
        for(int i = 0 ; i < observers.size() ; i++) {
            observers.valueAt(i).newValue();
        }
        super.setValue(value);
    }

    private static class ObserverWrapper<T> implements Observer<T> {

        private Observer<T> observer;

        private boolean pending = false;

        ObserverWrapper(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onChanged(T t) {
            if(pending) {
                pending = false;
                observer.onChanged(t);
            }
        }

        void newValue() {
            pending = true;
        }
    }
}
