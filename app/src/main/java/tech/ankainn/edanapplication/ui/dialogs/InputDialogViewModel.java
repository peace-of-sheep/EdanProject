package tech.ankainn.edanapplication.ui.dialogs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import tech.ankainn.edanapplication.ui.base.BaseViewModel;

import static tech.ankainn.edanapplication.ui.dialogs.InputDialogState.CLOSED;
import static tech.ankainn.edanapplication.ui.dialogs.InputDialogState.OPENED;

public class InputDialogViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> isOpen = new MutableLiveData<>();
    private MutableLiveData<CharSequence> data = new MutableLiveData<>();
    private LiveData<Boolean> color;

    public InputDialogViewModel(@NonNull Application application) {
        super(application);
        InputDialogState dialogState = getApplication().getInputDialogState();

        dialogState.getState().addSource(isOpen, open -> dialogState.setState(open ? OPENED : CLOSED));

        color = Transformations.map(data, s -> s != null && s.length() > 0);
    }

    public MutableLiveData<CharSequence> getData() {
        return data;
    }

    public void setData(CharSequence s) {
        data.setValue(s);
    }

    public LiveData<Boolean> getColor() {
        return color;
    }

    public void setState(boolean state) {
        isOpen.setValue(state);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        getApplication().getInputDialogState().getState().removeSource(isOpen);
    }
}
