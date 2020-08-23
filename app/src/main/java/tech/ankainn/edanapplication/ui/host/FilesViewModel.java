package tech.ankainn.edanapplication.ui.host;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.formOne.FormOneData;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.repositories.FormOneRepository;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.util.Tuple2;

public class FilesViewModel extends ViewModel {

    private MediatorLiveData<List<Tuple2<Boolean, FormOneData>>> listFormOne = new MediatorLiveData<>();
    private MediatorLiveData<List<Tuple2<Boolean, FormTwoData>>> listFormTwo = new MediatorLiveData<>();

    private long tempFormOneId = 0L;
    private long tempFormTwoId = 0L;

    public FilesViewModel(FormOneRepository formOneRepository, FormTwoRepository formTwoRepository) {

        LiveData<List<FormOneData>> sourceFormOne = formOneRepository.getAllFormsFromDb();
        listFormOne.addSource(sourceFormOne, source -> {
            if (!source.isEmpty()) {
                List<Tuple2<Boolean, FormOneData>> result = new ArrayList<>();
                for (FormOneData formOneData : source) result.add(new Tuple2<>(false, formOneData));
                listFormOne.setValue(result);
            }
        });

        LiveData<List<FormTwoData>> source = formTwoRepository.getAllFormsFromDb();
        listFormTwo.addSource(source, listData -> {
            if (listData == null) return;

            List<Tuple2<Boolean, FormTwoData>> result = new ArrayList<>();
            for (FormTwoData data : listData) {
                result.add(new Tuple2<>(false, data));
            }
            listFormTwo.setValue(result);
        });

        /*
        MutableLiveData<Metadata<FormTwoData>> loading = new MutableLiveData<>();
        list.addSource(loading, loadingData -> {
            if(list.getValue() == null) return;
            List<Metadata<FormTwoData>> temp = new ArrayList<>(list.getValue());

            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).data.id == loadingData.data.id) {
                    temp.set(i, loadingData);
                    list.setValue(temp);
                    return;
                }
            }
        });
        */

        /*
        MutableLiveData<FormTwoData> upload = new MutableLiveData<>();
        LiveData<Integer> result = Transformations.switchMap(upload, input -> formTwoRepository.postFormTwo(input));
        */
    }

    public LiveData<List<Tuple2<Boolean, FormOneData>>> getListFormOne() {
        return listFormOne;
    }

    public LiveData<List<Tuple2<Boolean, FormTwoData>>> getListFormTwo() {
        return listFormTwo;
    }

    public void setActiveItem(long formId) {
        tempFormTwoId = formId;
    }

    public long getActiveItem() {
        long copy = tempFormTwoId;
        tempFormTwoId = 0L;
        return copy;
    }
}
