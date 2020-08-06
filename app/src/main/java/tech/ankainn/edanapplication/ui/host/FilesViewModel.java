package tech.ankainn.edanapplication.ui.host;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.global.BottomOptions;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.ui.common.BaseViewModel;
import tech.ankainn.edanapplication.util.FormTwoFactory;
import tech.ankainn.edanapplication.util.SingleMsg;
import tech.ankainn.edanapplication.util.Metadata;

public class FilesViewModel extends BaseViewModel {

    private FormTwoRepository formTwoRepository;

    private MediatorLiveData<List<Metadata<FormTwoData>>> list = new MediatorLiveData<>();

    private Metadata<FormTwoData> activeItem;

    public FilesViewModel(@NonNull Application application) {
        super(application);
        formTwoRepository = getApplication().getFormTwoRepository();

        LiveData<List<FormTwoData>> source = formTwoRepository.getAllFormsFromDb();
        list.addSource(source, listData -> {
            if (listData == null || listData.size() == 0) return;

            List<Metadata<FormTwoData>> result = new ArrayList<>();
            for (FormTwoData data : listData) {
                result.add(new Metadata<>(data, false));
            }
            list.setValue(result);
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

    public LiveData<List<Metadata<FormTwoData>>> getList() {
        return list;
    }

    public void setActiveFile(FormTwoData formTwoData, boolean  loading) {
        FormTwoData temp = FormTwoFactory.cloneFormTwoData(formTwoData);
        activeItem = new Metadata<>(temp, loading);
    }

    public void createFormTwoFile() {
        formTwoRepository.createFormTwoData();
    }
}
