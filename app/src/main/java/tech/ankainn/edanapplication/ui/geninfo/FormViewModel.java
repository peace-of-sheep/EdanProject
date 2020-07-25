package tech.ankainn.edanapplication.ui.geninfo;

import androidx.lifecycle.LiveData;

import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;

public interface FormViewModel {
    LiveData<MapLocationData> getMapLocationData();
    LiveData<GenInfData> getGenInfData();
}
