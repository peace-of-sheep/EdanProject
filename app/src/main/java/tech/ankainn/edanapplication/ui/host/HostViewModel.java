package tech.ankainn.edanapplication.ui.host;

import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.repositories.UserRepository;

public class HostViewModel extends ViewModel {

    private UserRepository userRepository;

    public HostViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long getUserId() {
        return userRepository.getRawUserId();
    }
}
