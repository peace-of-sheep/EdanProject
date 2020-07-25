package tech.ankainn.edanapplication.ui.formTwo;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.List;

import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.HouseholdData;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.ui.common.BaseViewModel;
import tech.ankainn.edanapplication.util.FormTwoFactory;
import timber.log.Timber;

public class FormTwoViewModel extends BaseViewModel {

    private FormTwoRepository formTwoRepository;

    public FormTwoViewModel(@NonNull Application application) {
        super(application);
        formTwoRepository = getApplication().getFormTwoRepository();
    }

    public void postTest() {
        formTwoRepository.postTest();
    }

    public void collectData(MapLocationData mapLocationData, GenInfData genInfData,
                            HouseholdData householdData, List<MemberData> listMembers) {
        FormTwoData formTwoData = FormTwoFactory.create(genInfData, mapLocationData, householdData, listMembers);
        formTwoRepository.saveForm(formTwoData);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.tag("ViewModel").d("onCleared: FormTwoViewModel");
    }
}
