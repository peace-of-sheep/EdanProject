package tech.ankainn.edanapplication.ui.formOne;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import tech.ankainn.edanapplication.BaseApp;
import tech.ankainn.edanapplication.ui.base.BaseViewModel;

public class SwitchableViewModel extends BaseViewModel {

    boolean quantityVisibility;
    boolean switchVisibility;
    boolean bottomVisibility;
    String btnText;

    boolean[] originalData;
    boolean[] newData;

    MutableLiveData<String> text = new MutableLiveData<>();

    public SwitchableViewModel(@NonNull Application application) {
        super(application);
    }

    void setViewsVisibilities(boolean quantityVisibility, boolean switchVisibility, boolean bottomVisibility) {
        this.quantityVisibility = quantityVisibility;
        this.switchVisibility = switchVisibility;
        this.bottomVisibility = bottomVisibility;
    }

    void setBtnText(String text) {
        this.btnText = text;
    }

}
