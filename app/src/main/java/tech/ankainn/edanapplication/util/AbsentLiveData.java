package tech.ankainn.edanapplication.util;

import androidx.lifecycle.LiveData;

public class AbsentLiveData<T> extends LiveData<T> {

    private AbsentLiveData() {
        postValue(null);
    }

    public static <K> AbsentLiveData<K> create() {
        return new AbsentLiveData<>();
    }
}
