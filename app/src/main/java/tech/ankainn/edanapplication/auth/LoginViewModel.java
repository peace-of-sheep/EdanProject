package tech.ankainn.edanapplication.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import tech.ankainn.edanapplication.base.BaseViewModel;
import tech.ankainn.edanapplication.model.AuthCredentials;
import tech.ankainn.edanapplication.repositories.UserRepository;

public class LoginViewModel extends BaseViewModel {

    private UserRepository userRepository;

    private MutableLiveData<Boolean> viewFocusState = new MutableLiveData<>(true);

    private State state = State.WAIT;

    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private MutableLiveData<AuthCredentials> authCredentials = new MutableLiveData<>();
    private LiveData<String> authToken;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = getApplication().getUserRepository();
        authToken = Transformations.switchMap(authCredentials,
                input -> userRepository.loadUser(input));
    }

    public LiveData<Boolean> isFocusable() {
        return viewFocusState;
    }

    public void setFocusableView(boolean focusable) {
        viewFocusState.postValue(focusable);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<String> getAuthToken() {
        return authToken;
    }

    private enum State {
        WAIT,
        LOADING
    }
}
