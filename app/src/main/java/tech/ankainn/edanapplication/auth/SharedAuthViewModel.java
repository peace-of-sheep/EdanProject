package tech.ankainn.edanapplication.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedAuthViewModel extends ViewModel {

    private MutableLiveData<String> navigation = new MutableLiveData<>();

    public void navigatePassAuth(String role) {
        navigation.setValue(role);
    }

    public LiveData<String> getNavigator() {
        return navigation;
    }
}
