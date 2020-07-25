package tech.ankainn.edanapplication.ui.geninfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.ui.common.BaseViewModel;
import timber.log.Timber;

public class GenInfViewModel extends BaseViewModel {

    private MutableLiveData<GenInfData> genInfData = new MutableLiveData<>(new GenInfData());

    public GenInfViewModel(@NonNull Application application) {
        super(application);
        Timber.tag("ViewModel").i("GenInfViewModel");
    }

    public LiveData<GenInfData> getGenInfData() {
        return genInfData;
    }

    public GenInfData requestData() {
        return genInfData.getValue();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.tag("ViewModel").d("onCleared: GenInfVM");
    }
}
