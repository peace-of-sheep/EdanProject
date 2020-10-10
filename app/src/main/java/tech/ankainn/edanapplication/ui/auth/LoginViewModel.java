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

        LiveData<Boolean> result = Transformations.switchMap(authCredentials, userRepository::loadUser);

        singleEvent.addSource(result, loaded -> {
            State state = loaded ? State.SUCCESSFUL : State.ERROR;
            this.state.setValue(state);
            this.singleEvent.setValue(state);
        });
    }

    public void loadUser(AuthCredentials authCredentials) {
        state.setValue(State.LOADING);
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
