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
import tech.ankainn.edanapplication.repositories.UbigeoDangerRepository;

public class HeaderViewModel extends ViewModel {

    private GenInfRepository genInfRepository;

    private MediatorLiveData<String> nameDangerGroup = new MediatorLiveData<>();
    private MutableLiveData<List<DangerEntity>> listDangers = new MutableLiveData<>();
    private MutableLiveData<String> dangerOwnerCode = new MutableLiveData<>();

    private LiveData<String[]> dptosNames;
    private List<UbigeoLocation> dptosUbigeo;
    private MutableLiveData<Integer> dptosPos = new MutableLiveData<>();

    private LiveData<String[]> provNames;
    private List<UbigeoLocation> provUbigeo;
    private MutableLiveData<Integer> provPos = new MutableLiveData<>();

    private LiveData<String[]> distNames;
    private List<UbigeoLocation> distUbigeo;
    private MutableLiveData<Integer> distPos = new MutableLiveData<>();

    private LiveData<HeaderData> headerData;
    private HeaderData currentData;

    public HeaderViewModel(GenInfRepository genInfRepository, UbigeoDangerRepository ubigeoDangerRepository) {
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

        LiveData<List<UbigeoLocation>> dptosSource = ubigeoDangerRepository.loadDptosList();
        dptosNames = Transformations.map(dptosSource, list -> {
            if (list == null) {
                return null;
            } else {
                dptosUbigeo = list;
                String[] names = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    names[i] = list.get(i).name;
                }
                return names;
            }
        });

        LiveData<List<UbigeoLocation>> provSource = Transformations.switchMap(dptosPos, pos -> {
            String dptoCode = dptosUbigeo.get(pos).code;
            return ubigeoDangerRepository.loadProvList(dptoCode);
        });
        provNames = Transformations.map(provSource, list -> {
            if (list != null) {
                provUbigeo = list;
                String[] names = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    names[i] = list.get(i).name;
                }
                return names;
            }
            return null;
        });

        LiveData<List<UbigeoLocation>> distSource = Transformations.switchMap(provPos, pos -> {
            String provCode = provUbigeo.get(pos).code;
            return ubigeoDangerRepository.loadDistList(provCode);
        });
        distNames = Transformations.map(distSource, list -> {
            if (list != null) {
                distUbigeo = list;
                String[] names = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    names[i] = list.get(i).name;
                }
                return names;
            }
            return null;
        });
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

    public LiveData<String[]> getDptosNames() {
        return dptosNames;
    }
    public LiveData<String[]> getProvNames() {
        return provNames;
    }
    public LiveData<String[]> getDistNames() {
        return distNames;
    }

    public void onDanger(DangerEntity danger) {
        currentData.danger = danger.name;
        currentData.codeDanger = danger.code;
        dangerOwnerCode.setValue(danger.ownerCode);
    }

    public void onDepartmentPos(int pos) {
        currentData.department = dptosUbigeo.get(pos).name;
        currentData.codeDepartment = dptosUbigeo.get(pos).code;

        dptosPos.setValue(pos);
    }
    public void onProvincePos(int pos) {
        currentData.province = provUbigeo.get(pos).name;
        currentData.codeProvince = provUbigeo.get(pos).code;

        provPos.setValue(pos);
    }
    public void onDistrictPos(int pos) {
        currentData.district = distUbigeo.get(pos).name;
        currentData.codeDistrict = distUbigeo.get(pos).code;
    }
}
