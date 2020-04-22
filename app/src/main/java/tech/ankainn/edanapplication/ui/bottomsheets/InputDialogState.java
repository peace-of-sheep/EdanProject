package tech.ankainn.edanapplication.ui.bottomsheets;

import androidx.annotation.IntDef;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import timber.log.Timber;

public final class InputDialogState {

    public static final int OPENED = 1;
    public static final int CLOSED = 0;

    @IntDef({OPENED, CLOSED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DialogState {}

    private static InputDialogState instance;

    private MediatorLiveData<Integer> open = new MediatorLiveData<>();

    public static InputDialogState getInstance() {
        if(instance == null) {
            instance = new InputDialogState();
            instance.setState(CLOSED);
        }
        return instance;
    }

    public void setState(@DialogState int state) {
        open.setValue(state);
    }

    public MediatorLiveData<Integer> getState() {
        return open;
    }
}
