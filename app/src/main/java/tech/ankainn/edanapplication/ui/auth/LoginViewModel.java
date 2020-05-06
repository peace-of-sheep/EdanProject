package tech.ankainn.edanapplication.ui.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import tech.ankainn.edanapplication.ui.base.BaseViewModel;
import tech.ankainn.edanapplication.ui.dialogs.InputDialogState;

public class LoginViewModel extends BaseViewModel {

    public static final int NO_VIEW = 0;

    private MediatorLiveData<Integer> viewId = new MediatorLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);
        viewId.addSource(getApplication().getInputDialogState().getState(), state -> {
            if(state == InputDialogState.CLOSED) {
                viewId.setValue(NO_VIEW);
            }
        });
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading.setValue(loading);
    }

    public void onTextViewFocused(int viewId, boolean hasFocus) {
        if(hasFocus) {
            this.viewId.setValue(viewId);
        }
    }

    public LiveData<Integer> getViewId() {
        return viewId;
    }
}
