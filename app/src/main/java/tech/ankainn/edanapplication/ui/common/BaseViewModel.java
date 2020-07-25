package tech.ankainn.edanapplication.ui.common;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import tech.ankainn.edanapplication.BaseApp;

public abstract class BaseViewModel extends AndroidViewModel {

    private BaseApp app;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        app = (BaseApp) application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public BaseApp getApplication() {
        return app;
    }
}
