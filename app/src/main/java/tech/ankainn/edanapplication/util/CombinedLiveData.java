package tech.ankainn.edanapplication.util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.internal.EverythingIsNonNull;

public class CombinedLiveData<T, K, S> extends MediatorLiveData<S> {

    private T data1;
    private K data2;

    public CombinedLiveData(LiveData<T> source1, LiveData<K> source2, CombineFunction<T, K, S> combine) {
        Executor executor = Executors.newSingleThreadExecutor();
        super.addSource(source1, it -> {
            executor.execute(() -> {
                data1 = it;
                postValue(combine.apply(data1, data2));
            });
        });

        super.addSource(source2, it -> {
            executor.execute(() -> {
                data2 = it;
                postValue(combine.apply(data1, data2));
            });
        });
    }

    @Override
    @EverythingIsNonNull
    public <S1> void addSource(LiveData<S1> source, Observer<? super S1> onChanged) {
        throw new UnsupportedOperationException();
    }

    @Override
    @EverythingIsNonNull
    public <S1> void removeSource(LiveData<S1> toRemote) {
        throw new UnsupportedOperationException();
    }

    public interface CombineFunction<T, K, S> {
        S apply(T first, K second);
    }
}
