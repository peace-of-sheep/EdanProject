package tech.ankainn.edanapplication.global;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tech.ankainn.edanapplication.util.Tuple2;

class CallbackWrapper<T> implements DefaultLifecycleObserver {

    private List<Tuple2<LifecycleOwner, T>> list;

    CallbackWrapper() {
        list = new ArrayList<>();
    }

    void observe(LifecycleOwner owner, T callback) {
        Tuple2<LifecycleOwner, T> tuple2 = new Tuple2<>(owner, callback);
        list.add(tuple2);
        owner.getLifecycle().addObserver(this);
    }

    void dispatch(Iterator<T> iterator) {
        for (Tuple2<LifecycleOwner, T> item : list) {
            iterator.map(item.second);
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        for (Tuple2<LifecycleOwner, T> tuple2 : list) {
            if(Objects.equals(tuple2.first, owner)) {
                list.remove(tuple2);
                return;
            }
        }
    }

    public interface Iterator<C> {
        void map(C callback);
    }
}
