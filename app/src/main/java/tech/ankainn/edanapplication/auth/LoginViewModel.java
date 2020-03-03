package tech.ankainn.edanapplication.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import tech.ankainn.edanapplication.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel {

    private State state = State.INIT;

    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadUser() {
        //TODO start connection to backend
        if(state != State.LOADING) {
            loading.postValue(true);
            state = State.LOADING;
        }
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    private enum State {
        INIT,
        LOADING
    }
}
