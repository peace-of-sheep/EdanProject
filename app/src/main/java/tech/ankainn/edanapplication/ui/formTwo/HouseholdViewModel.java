package tech.ankainn.edanapplication.ui.formTwo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import tech.ankainn.edanapplication.model.formTwo.HouseholdData;
import tech.ankainn.edanapplication.ui.common.BaseViewModel;
import timber.log.Timber;

public class HouseholdViewModel extends BaseViewModel {

    private MutableLiveData<HouseholdData> householdData = new MutableLiveData<>(new HouseholdData());

    public HouseholdViewModel(@NonNull Application application) {
        super(application);
        Timber.tag("ViewModel").i("HouseholdViewModel:");
    }

    public LiveData<HouseholdData> getHouseholdData() {
        return householdData;
    }

    public HouseholdData requestData() {
        return householdData.getValue();
    }

    public void setIdFloor(int position) {
        Timber.v("setIdFloor: %s", position);
        householdData.getValue().idFloor = position;
    }

    public void setIdWall(int position) {
        Timber.v("setIdWall: %s", position);
        householdData.getValue().idWall = position;
    }
    public void setIdRoof(int position) {
        Timber.v("setIdRoof: %s", position);
        householdData.getValue().idRoof = position;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.tag("ViewModel").d("onCleared: HouseholdViewModel");
    }
}
