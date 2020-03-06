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

    private State state = State.WAIT;

    private CharSequence userInputTemp = "";
    private CharSequence passInputTemp = "";

    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private MutableLiveData<AuthCredentials> authCredentials = new MutableLiveData<>();
    private LiveData<String> authToken;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = getApplication().getUserRepository();
        authToken = Transformations.switchMap(authCredentials,
                input -> userRepository.loadUser(input));
    }

    public void changeUserInput(CharSequence userInput) {
        userInputTemp = userInput;
    }

    public void changePassInput(CharSequence passInput) {
        passInputTemp = passInput;
    }

    public void sendCredentials() {
        //TODO start connection to backend
        if(state != State.LOADING) {
            state = State.LOADING;
            authCredentials.setValue(new AuthCredentials(userInputTemp.toString(), passInputTemp.toString()));
            loading.postValue(true);
        }
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
