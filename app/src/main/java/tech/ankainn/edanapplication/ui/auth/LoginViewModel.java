package tech.ankainn.edanapplication.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.model.api.auth.AuthCredentials;
import tech.ankainn.edanapplication.repositories.UserRepository;
import tech.ankainn.edanapplication.util.SingleLiveData;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<AuthCredentials> authCredentials = new MutableLiveData<>();

    private MediatorLiveData<State> state = new MediatorLiveData<>();

    private SingleLiveData<State> singleEvent = new SingleLiveData<>();

    public LoginViewModel(UserRepository userRepository) {

        state.setValue(State.STILL);

        LiveData<Boolean> result = Transformations.switchMap(authCredentials, authCredentials -> {
            state.setValue(State.LOADING);
            return userRepository.loadUser(authCredentials);
        });

        state.addSource(result, loaded -> state.setValue(loaded ? State.SUCCESSFUL : State.ERROR));

        singleEvent.addSource(state, currentState -> {
            if (currentState == State.ERROR || currentState == State.SUCCESSFUL) {
                singleEvent.setValue(currentState);
            }
        });
    }

    public void loadUser(AuthCredentials authCredentials) {
        this.authCredentials.setValue(authCredentials);
    }

    public LiveData<State> getState() {
        return state;
    }

    public LiveData<State> getSingleEvent() {
        return singleEvent;
    }

    public enum State {
        STILL,
        LOADING,
        ERROR,
        SUCCESSFUL
    }
}
