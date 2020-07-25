package tech.ankainn.edanapplication.ui.camera;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.media.PhotoData;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<List<PhotoData>> list = new MutableLiveData<>();

    public LiveData<List<PhotoData>> getList() {
        return list;
    }

    public void addPhoto(String filePath, String description) {
        PhotoData photoData = new PhotoData();
        photoData.filePath = filePath;
        photoData.description = description;

        List<PhotoData> temp = list.getValue() == null ? new ArrayList<>() : new ArrayList<>(list.getValue());
        temp.add(photoData);

        list.setValue(temp);
    }

    public boolean isEmpty() {
        return list.getValue() == null || list.getValue().size() == 0;
    }
}
