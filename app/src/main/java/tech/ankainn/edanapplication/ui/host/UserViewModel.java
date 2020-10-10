package tech.ankainn.edanapplication.ui.host;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.model.app.auth.UserData;
import tech.ankainn.edanapplication.repositories.UserRepository;

public class UserViewModel extends ViewModel {

    private LiveData<UserData> userData;

    public UserViewModel(UserRepository userRepository) {
        userData = userRepository.getCurrentUser();
    }

    public LiveData<UserData> getUserData() {
        return userData;
    }
}
