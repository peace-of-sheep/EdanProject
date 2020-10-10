package tech.ankainn.edanapplication.ui.formOne;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.model.app.formOne.FormOneData;
import tech.ankainn.edanapplication.model.app.formOne.SelectableData;
import tech.ankainn.edanapplication.repositories.FormOneRepository;

public class SwitchableViewModel extends ViewModel {

    private MediatorLiveData<SelectableData> damageOne = new MediatorLiveData<>();
    private MediatorLiveData<SelectableData> damageTwo = new MediatorLiveData<>();
    private MediatorLiveData<SelectableData> damageThree = new MediatorLiveData<>();
    private MediatorLiveData<SelectableData> activities = new MediatorLiveData<>();
    private MediatorLiveData<SelectableData> activitiesOthers = new MediatorLiveData<>();
    private MediatorLiveData<SelectableData> needs = new MediatorLiveData<>();
    private MediatorLiveData<SelectableData> needsOthers = new MediatorLiveData<>();

    public SwitchableViewModel(FormOneRepository formOneRepository) {
        LiveData<FormOneData> source = formOneRepository.loadCacheFormOneData();

        damageOne.addSource(source, formOneData -> {
            if (formOneData != null) {
                damageOne.setValue(formOneData.damageOne);
            }
        });
        damageTwo.addSource(source, formOneData -> {
            if (formOneData != null) {
                damageTwo.setValue(formOneData.damageTwo);
            }
        });
        damageThree.addSource(source, formOneData -> {
            if (formOneData != null) {
                damageThree.setValue(formOneData.damageThree);
            }
        });

        activities.addSource(source, formOneData -> {
            if (formOneData != null) {
                activities.setValue(formOneData.activities);
            }
        });
        activitiesOthers.addSource(source, formOneData -> {
            if (formOneData != null) {
                activitiesOthers.setValue(formOneData.activitiesOthers);
            }
        });

        needs.addSource(source, formOneData -> {
            if (formOneData != null) {
                needs.setValue(formOneData.needs);
            }
        });
        needsOthers.addSource(source, formOneData -> {
            if (formOneData != null) {
                needsOthers.setValue(formOneData.needsOthers);
            }
        });
    }

    public LiveData<SelectableData> getDamageOne() {
        return damageOne;
    }

    public LiveData<SelectableData> getDamageTwo() {
        return damageTwo;
    }

    public LiveData<SelectableData> getDamageThree() {
        return damageThree;
    }

    public LiveData<SelectableData> getActivities() {
        return activities;
    }

    public LiveData<SelectableData> getActivitiesOthers() {
        return activitiesOthers;
    }

    public LiveData<SelectableData> getNeeds() {
        return needs;
    }

    public LiveData<SelectableData> getNeedsOthers() {
        return needsOthers;
    }
}
