package tech.ankainn.edanapplication.ui.host;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.dto.FormOneSubset;
import tech.ankainn.edanapplication.model.dto.FormTwoSubset;
import tech.ankainn.edanapplication.repositories.FormOneRepository;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.util.SingleLiveData;
import tech.ankainn.edanapplication.util.Tuple2;

public class FilesViewModel extends ViewModel {

    private FormTwoRepository formTwoRepository;

    private MediatorLiveData<List<Tuple2<Boolean, FormOneSubset>>> listFormOne = new MediatorLiveData<>();
    //private MediatorLiveData<List<Pair<Boolean, FormTwoSubset>>> listFormTwo = new MediatorLiveData<>();
    private LiveData<List<FormTwoSubset>> listFormTwo;

    private MutableLiveData<Long> idUpload = new MutableLiveData<>();
    private SingleLiveData<State> singleEvent = new SingleLiveData<>();

    private long tempFormOneId = 0L;
    private long tempFormTwoId = 0L;

    private int tempPos = -1;

    public FilesViewModel(FormOneRepository formOneRepository, FormTwoRepository formTwoRepository) {
        this.formTwoRepository = formTwoRepository;

        listFormTwo = formTwoRepository.loadAllFormTwoSubset();

        LiveData<List<FormOneSubset>> sourceFormOne = formOneRepository.loadAllFormOneSubset();
        listFormOne.addSource(sourceFormOne, list -> {
            if (!list.isEmpty()) {
                List<Tuple2<Boolean, FormOneSubset>> result = new ArrayList<>();
                for (FormOneSubset formOneSubset : list) result.add(new Tuple2<>(false, formOneSubset));
                listFormOne.setValue(result);
            }
        });

        LiveData<Boolean> resultUpload = Transformations.switchMap(idUpload, formTwoRepository::uploadFormTwoById);
        singleEvent.addSource(resultUpload, result -> {
            if (result) {
                singleEvent.setValue(State.SUCCESS);
            } else {
                singleEvent.setValue(State.ERROR);
            }
        });
    }

    public LiveData<List<Tuple2<Boolean, FormOneSubset>>> getListFormOne() {
        return listFormOne;
    }

    public LiveData<List<FormTwoSubset>> getListFormTwo() {
        return listFormTwo;
    }

    public SingleLiveData<State> getSingleEvent() {
        return singleEvent;
    }

    public void setTempFormTwoId(long formId) {
        tempFormTwoId = formId;
    }

    public long getTempFormTwoId() {
        long copy = tempFormTwoId;
        tempFormTwoId = 0L;
        return copy;
    }

    public void uploadFormTwo(long formTwoId) {
        idUpload.setValue(formTwoId);
    }

    public enum State {
        STILL,
        LOADING,
        SUCCESS,
        ERROR
    }
}
