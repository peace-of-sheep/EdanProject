package tech.ankainn.edanapplication.ui.auth;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.model.api.auth.AuthCredentials;
import tech.ankainn.edanapplication.model.app.auth.UserData;
import tech.ankainn.edanapplication.repositories.UbigeoRepository;
import tech.ankainn.edanapplication.repositories.UserRepository;
import tech.ankainn.edanapplication.util.AbsentLiveData;
import tech.ankainn.edanapplication.util.SingleLiveData;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<AuthCredentials> authCredentials = new MutableLiveData<>();

    private MediatorLiveData<State> state = new MediatorLiveData<>();

    private SingleLiveData<State> singleEvent = new SingleLiveData<>();

    public LoginViewModel(UserRepository userRepository, UbigeoRepository ubigeoRepository) {

        LiveData<UserData> userResult = Transformations.switchMap(authCredentials, authCredentials -> {

            if (TextUtils.isEmpty(authCredentials.getUsername())) {
                state.setValue(State.NO_USER);
                return null;
            } else if (TextUtils.isEmpty(authCredentials.getClave())) {
                state.setValue(State.NO_PASS);
                return null;
            } else {

                state.setValue(State.LOADING);
                return userRepository.loadUser(authCredentials);
            }
        });

        LiveData<Boolean> ubigeoResult = Transformations.switchMap(userResult, user -> {
            if (user == null) return new MutableLiveData<>(false);
            else {
                return ubigeoRepository.loadUbigeos(user.ubigeo);
            }
        });

        singleEvent.addSource(ubigeoResult, loaded -> {
            State state = loaded ? State.SUCCESSFUL : State.ERROR;
            this.state.setValue(state);
            this.singleEvent.setValue(state);
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
        NO_USER,
        NO_PASS,
        ERROR,
        SUCCESSFUL
    }
}
