package tech.ankainn.edanapplication.ui.geninfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import tech.ankainn.edanapplication.danger.DangerEntity;
import tech.ankainn.edanapplication.model.app.ubigeo.UbigeoLocation;
import tech.ankainn.edanapplication.model.app.geninf.HeaderData;
import tech.ankainn.edanapplication.repositories.GenInfRepository;
import tech.ankainn.edanapplication.repositories.UbigeoRepository;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class HeaderViewModel extends ViewModel {

    private GenInfRepository genInfRepository;

    private MediatorLiveData<String> nameDangerGroup = new MediatorLiveData<>();
    private MutableLiveData<List<DangerEntity>> listDangers = new MutableLiveData<>();
    private MutableLiveData<String> dangerOwnerCode = new MutableLiveData<>();

    private LiveData<List<UbigeoLocation>> listDept;
    private LiveData<List<UbigeoLocation>> listProv;
    private LiveData<List<UbigeoLocation>> listDist;
    private LiveData<List<UbigeoLocation>> listLocal;

    private MutableLiveData<String> codeDept = new MutableLiveData<>();
    private MutableLiveData<String> codeProv = new MutableLiveData<>();
    private MutableLiveData<String> codeDist = new MutableLiveData<>();

    private LiveData<HeaderData> headerData;
    private HeaderData currentData;

    public HeaderViewModel(GenInfRepository genInfRepository, UbigeoRepository ubigeoRepository) {
        this.genInfRepository = genInfRepository;

        LiveData<HeaderData> source = genInfRepository.loadHeaderData();
        headerData = Transformations.map(source, headerData -> {
            this.currentData = headerData;
            return headerData;
        });

        List<DangerEntity> listDangers = genInfRepository.loadDangers();
        this.listDangers.setValue(listDangers);

        nameDangerGroup.addSource(dangerOwnerCode, ownerCode -> {
            DangerEntity groupDanger = genInfRepository.loadGroupDanger(ownerCode);
            currentData.groupDanger = groupDanger.name;
            currentData.codeGroupDanger = groupDanger.code;
            nameDangerGroup.setValue(groupDanger.name);
        });

        listDept = ubigeoRepository.loadDptosList();
        listProv = Transformations.switchMap(codeDept, ubigeoRepository::loadProvList);
        listDist = Transformations.switchMap(codeProv, ubigeoRepository::loadDistList);
        listLocal = Transformations.switchMap(codeDist, ubigeoRepository::loadLocalList);
    }

    public LiveData<HeaderData> getHeaderData() {
        return headerData;
    }

    public LiveData<String> getNameDangerGroup() {
        return nameDangerGroup;
    }
    public LiveData<List<DangerEntity>> getListDangers() {
        return listDangers;
    }

    public LiveData<List<UbigeoLocation>> getListDept() {
        return listDept;
    }
    public LiveData<List<UbigeoLocation>> getListProv() {
        return listProv;
    }
    public LiveData<List<UbigeoLocation>> getListDist() {
        return listDist;
    }
    public LiveData<List<UbigeoLocation>> getListLocal() {
        return listLocal;
    }

    public void onDanger(DangerEntity danger) {
        currentData.danger = danger.name;
        currentData.codeDanger = danger.code;
        dangerOwnerCode.setValue(danger.ownerCode);
    }

    public void onDepartment(UbigeoLocation ubigeoLocation) {
        currentData.department = ubigeoLocation.name;
        currentData.codeDepartment = ubigeoLocation.code;

        codeDept.setValue(ubigeoLocation.code);
    }
    public void onProvince(UbigeoLocation ubigeoLocation) {
        currentData.province = ubigeoLocation.name;
        currentData.codeProvince = ubigeoLocation.code;

        codeProv.setValue(ubigeoLocation.code);
    }
    public void onDistrict(UbigeoLocation ubigeoLocation) {
        currentData.district = ubigeoLocation.name;
        currentData.codeDistrict = ubigeoLocation.code;

        codeDist.setValue(ubigeoLocation.code);
    }
    public void onLocality(UbigeoLocation ubigeoLocation) {
        currentData.locality = ubigeoLocation.name;
        currentData.codeLocality = ubigeoLocation.code;
    }
}
