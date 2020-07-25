package tech.ankainn.edanapplication.ui.formOne;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import tech.ankainn.edanapplication.ui.common.BaseViewModel;

public class SwitchableViewModel extends BaseViewModel {

    MutableLiveData<String> text = new MutableLiveData<>();

    public SwitchableViewModel(@NonNull Application application) {
        super(application);
    }

}
