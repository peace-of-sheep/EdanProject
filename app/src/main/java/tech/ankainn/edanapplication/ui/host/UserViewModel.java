package tech.ankainn.edanapplication.ui.host;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.model.app.auth.UserData;
import tech.ankainn.edanapplication.repositories.UserRepository;
import tech.ankainn.edanapplication.util.SingleLiveData;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;

    private LiveData<UserData> userData;
    private SingleLiveData<State> status = new SingleLiveData<>();

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        userData = userRepository.getCurrentUser();
        status.setValue(State.LOGIN);
    }

    public LiveData<UserData> getUserData() {
        return userData;
    }

    public LiveData<State> getStatus() {
        return status;
    }

    public void logout() {
        userRepository.logout();
        status.setValue(State.LOGOUT);
    }

    public enum State {
        LOGIN,
        LOGOUT
    }
}
