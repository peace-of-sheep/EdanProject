package tech.ankainn.edanapplication.ui.formTwoB;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.factory.FormTwoFactory;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;

public class LivelihoodViewModel extends ViewModel {

    private MediatorLiveData<List<LivelihoodData>> copyList = new MediatorLiveData<>();

    private MutableLiveData<Long> tempId = new MutableLiveData<>();
    private MediatorLiveData<LivelihoodData> dialogLivelihood = new MediatorLiveData<>();

    private List<LivelihoodData> originalList;

    private long countTempId = 0;

    public LivelihoodViewModel(FormTwoRepository formTwoRepository) {
        LiveData<FormTwoData> source = formTwoRepository.getCurrentFormTwoData();

        copyList.addSource(source, formTwoData -> {
            if (formTwoData != null && formTwoData.listLivelihood != null) {
                originalList = formTwoData.listLivelihood;

                for (LivelihoodData livelihoodData : originalList) {
                    livelihoodData.tempId = ++countTempId;
                }

                copyList.setValue(new ArrayList<>(originalList));
            }
        });

        dialogLivelihood.addSource(tempId, tempId -> {
            if (tempId == 0L) {
                LivelihoodData result = FormTwoFactory.createEmptyLivelihoodData();
                dialogLivelihood.setValue(result);
            } else {
                for (LivelihoodData livelihoodData : originalList) {
                    if (livelihoodData.tempId == tempId) {
                        LivelihoodData update = FormTwoFactory.cloneLivelihoodData(livelihoodData);
                        dialogLivelihood.setValue(update);
                        return;
                    }
                }
            }
        });
    }

    public LiveData<List<LivelihoodData>> getList() {
        return copyList;
    }

    public LiveData<LivelihoodData> getLivelihoodData() {
        return dialogLivelihood;
    }

    public void pushActiveItem() {
        LivelihoodData livelihoodData = dialogLivelihood.getValue();
        if (livelihoodData == null) return;

        livelihoodData.dataVersion++;

        if (livelihoodData.tempId == 0L) {
            livelihoodData.tempId = ++countTempId;
            originalList.add(livelihoodData);
        } else {
            for (int i = 0; i < originalList.size(); i++) {
                if (originalList.get(i).tempId == livelihoodData.tempId) {
                    originalList.set(i, livelihoodData);
                    break;
                }
            }
        }

        copyList.setValue(new ArrayList<>(originalList));
    }

    public void searchLivelihoodById(long tempId) {
        this.tempId.setValue(tempId);
    }
}
