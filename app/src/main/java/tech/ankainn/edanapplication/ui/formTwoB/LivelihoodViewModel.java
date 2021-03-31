package tech.ankainn.edanapplication.ui.formTwoB;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.model.app.master.DataEntity;
import tech.ankainn.edanapplication.repositories.LivelihoodRepository;
import tech.ankainn.edanapplication.repositories.MemberRepository;

public class LivelihoodViewModel extends ViewModel {

    private LivelihoodRepository livelihoodRepository;

    private LiveData<List<DataEntity>> livelihoodNames;
    private MutableLiveData<DataEntity> selectedLivelihoodName = new MutableLiveData<>();

    private LiveData<List<DataEntity>> livelihoodTypeNames;

    private MutableLiveData<Pair<Long, Long>> tempIds = new MutableLiveData<>();
    private MediatorLiveData<LivelihoodData> livelihoodData = new MediatorLiveData<>();
    private LivelihoodData currentLivelihoodData;

    public LivelihoodViewModel(LivelihoodRepository livelihoodRepository) {
        this.livelihoodRepository = livelihoodRepository;

        livelihoodData.addSource(tempIds, pair -> {
            LivelihoodData livelihoodData = livelihoodRepository.loadLivelihoodData(pair.first, pair.second);

            if (livelihoodData != null) {
                this.currentLivelihoodData = livelihoodData;
                this.livelihoodData.setValue(livelihoodData);
            }
        });

        livelihoodNames = livelihoodRepository.loadLivelihoodNames();

        livelihoodTypeNames = Transformations.switchMap(selectedLivelihoodName,
                data -> livelihoodRepository.loadLivelihoodTypeNames(data.code));
    }

    public LiveData<LivelihoodData> getLivelihoodData() {
        return livelihoodData;
    }

    public LiveData<List<DataEntity>> getLivelihoodNames() {
        return livelihoodNames;
    }

    public LiveData<List<DataEntity>> getLivelihoodTypeNames() {
        return livelihoodTypeNames;
    }

    public void loadLivelihoodData(long tempMemberId, long tempId) {
        this.tempIds.setValue(Pair.create(tempMemberId, tempId));
    }

    public void saveLivelihoodData() {
        if (currentLivelihoodData != null) {
            currentLivelihoodData.dataVersion++;
            livelihoodRepository.saveLivelihoodData(currentLivelihoodData);
        }
    }

    public void onLivelihoodName(DataEntity data) {
        currentLivelihoodData.code = data.code;
        currentLivelihoodData.name = data.name;
        selectedLivelihoodName.setValue(data);
    }

    public void onLivelihoodTypeName(DataEntity data) {
        currentLivelihoodData.codeType = data.code;
        currentLivelihoodData.type = data.name;
    }
}
