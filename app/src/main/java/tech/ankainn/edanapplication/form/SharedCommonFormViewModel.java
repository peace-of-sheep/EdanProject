package tech.ankainn.edanapplication.form;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

public class SharedCommonFormViewModel extends AndroidViewModel {

    private MutableLiveData<LatLng> location = new MutableLiveData<>();

    public SharedCommonFormViewModel(@NonNull Application application) {
        super(application);
    }

    public void setLocation(LatLng center) {
        location.postValue(center);
    }

    public LiveData<LatLng> getLocation() {
        return location;
    }
}
