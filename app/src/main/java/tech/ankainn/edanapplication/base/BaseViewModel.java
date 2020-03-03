package tech.ankainn.edanapplication.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import tech.ankainn.edanapplication.BaseApp;

public class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    public BaseApp getApplication() {
        return super.getApplication();
    }
}
