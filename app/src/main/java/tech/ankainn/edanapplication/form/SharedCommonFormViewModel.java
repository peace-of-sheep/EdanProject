package tech.ankainn.edanapplication.form;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SharedCommonFormViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> fullscreen = new MutableLiveData<>();
    private boolean immersiveMode = false;

    public SharedCommonFormViewModel(@NonNull Application application) {
        super(application);
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen.postValue(fullscreen);
    }

    public LiveData<Boolean> getFullscreen() {
        return fullscreen;
    }
}
