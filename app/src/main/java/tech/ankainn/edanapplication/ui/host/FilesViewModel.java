package tech.ankainn.edanapplication.ui.host;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import tech.ankainn.edanapplication.model.dto.FormOneSubset;
import tech.ankainn.edanapplication.model.dto.FormTwoSubset;
import tech.ankainn.edanapplication.repositories.FormOneRepository;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.repositories.UserRepository;
import tech.ankainn.edanapplication.util.AbsentLiveData;
import tech.ankainn.edanapplication.util.SingleLiveData;

public class FilesViewModel extends ViewModel {

    private FormTwoRepository formTwoRepository;
    private UserRepository userRepository;

    private LiveData<List<FormOneSubset>> listFormOne;
    private LiveData<List<FormTwoSubset>> listFormTwo;

    private MutableLiveData<Long> idUpload = new MutableLiveData<>();
    private SingleLiveData<State> singleEvent = new SingleLiveData<>();

    private long tempFormOneId = 0L;
    private long tempFormTwoId = 0L;

    private int tempPos = -1;

    public FilesViewModel(FormOneRepository formOneRepository, FormTwoRepository formTwoRepository, UserRepository userRepository) {
        this.formTwoRepository = formTwoRepository;
        this.userRepository = userRepository;

        LiveData<Long> userId = userRepository.getUserId();

        listFormTwo = Transformations.switchMap(userId, id ->
                id == -1L ? AbsentLiveData.create() : formTwoRepository.loadAllFormTwoSubset(id));

        //listFormTwo = formTwoRepository.loadAllFormTwoSubset();

        listFormOne = Transformations.switchMap(userId, id ->
                id == -1L ? AbsentLiveData.create() : formOneRepository.loadAllFormOneSubset(id));

        LiveData<Boolean> resultUpload = Transformations.switchMap(idUpload, formTwoRepository::uploadFormTwoById);
        singleEvent.addSource(resultUpload, result -> {
            if (result) {
                singleEvent.setValue(State.SUCCESS);
            } else {
                singleEvent.setValue(State.ERROR);
            }
        });
    }

    public LiveData<List<FormOneSubset>> getListFormOne() {
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

    public long getUserId() {
        return userRepository.getRawUserId();
    }
    public String getUsername() {
        return userRepository.getRawUsername();
    }

    public void uploadFormTwo(long formTwoId) {
        idUpload.setValue(formTwoId);
    }

    public void removeFormTwo(long formTwoId) {
        formTwoRepository.removeForTwo(formTwoId);
    }

    public enum State {
        STILL,
        LOADING,
        SUCCESS,
        ERROR
    }
}
