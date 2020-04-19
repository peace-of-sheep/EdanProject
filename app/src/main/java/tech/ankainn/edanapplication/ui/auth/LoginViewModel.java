package tech.ankainn.edanapplication.ui.auth;

import android.app.Application;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import tech.ankainn.edanapplication.ui.base.BaseViewModel;
import tech.ankainn.edanapplication.util.LiveEvent;

public class LoginViewModel extends BaseViewModel {

    public static final int NO_VIEW = 0;

    public LiveEvent<Integer> viewId = new LiveEvent<>();

    public MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void onTextViewFocused(int viewId, boolean hasFocus) {
        this.viewId.setValue(hasFocus ? viewId : NO_VIEW);
    }
}
