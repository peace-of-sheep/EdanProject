package tech.ankainn.edanapplication.ui.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import tech.ankainn.edanapplication.ui.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading.setValue(loading);
    }
}
