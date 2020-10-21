package tech.ankainn.edanapplication.ui.geninfo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.danger.DangerGroup;
import tech.ankainn.edanapplication.danger.DangerType;
import tech.ankainn.edanapplication.model.app.ubigeo.UbigeoLocation;
import tech.ankainn.edanapplication.model.app.geninf.HeaderData;
import tech.ankainn.edanapplication.repositories.GenInfRepository;
import tech.ankainn.edanapplication.repositories.UbigeoDangerRepository;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class HeaderViewModel extends ViewModel {

    private GenInfRepository genInfRepository;

    private MutableLiveData<String[]> dangerGroupText = new MutableLiveData<>();
    private MutableLiveData<Integer> groupPos = new MutableLiveData<>();
    private LiveData<String[]> dangerTypeText;

    private List<DangerGroup> dangerGroupList;
    private List<DangerType> dangerTypeList;

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

    private final int arrayDepartmentRes = R.array.departments;

    public HeaderViewModel(GenInfRepository genInfRepository, UbigeoDangerRepository ubigeoDangerRepository) {
        this.genInfRepository = genInfRepository;

        dangerGroupList = genInfRepository.loadDangerGroupList();
        String[] dangerGroupText = new String[dangerGroupList.size()];
        for (int i = 0; i < dangerGroupList.size(); i++) {
            dangerGroupText[i] = dangerGroupList.get(i).name;
        }
        this.dangerGroupText.setValue(dangerGroupText);
        dangerTypeText = Transformations.map(groupPos, pos -> {
            dangerTypeList = dangerGroupList.get(pos).listDangers;
            String[] dangerTypeText = new String[dangerTypeList.size()];
            for (int i = 0; i < dangerTypeList.size(); i++) {
                dangerTypeText[i] = dangerTypeList.get(i).name;
            }
            return dangerTypeText;
        });

        LiveData<HeaderData> source = genInfRepository.loadHeaderData();
        headerData = Transformations.map(source, headerData -> {
            this.currentData = headerData;
            return headerData;
        });

        LiveData<List<UbigeoLocation>> dptosSource = ubigeoDangerRepository.loadDptosList();
        dptosNames = Transformations.map(dptosSource, list -> {
            Timber.tag(Tagger.DUMPER).w("HeaderViewModel.HeaderViewModel: dpto %s", list);
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
            Timber.tag(Tagger.DUMPER).w("HeaderViewModel.HeaderViewModel: prov %s", list);
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
            Timber.tag(Tagger.DUMPER).w("HeaderViewModel.HeaderViewModel: dist %s", list);
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

    public LiveData<String[]> getDangerGroupText() {
        return dangerGroupText;
    }
    public LiveData<String[]> getDangerTypeText() {
        return dangerTypeText;
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

    public void onDangerGroupPos(int pos) {
        currentData.groupDanger = dangerGroupList.get(pos).name;
        currentData.codeGroupDanger = dangerGroupList.get(pos).code;
        groupPos.setValue(pos);
    }
    public void onDangerTypePos(int pos) {
        currentData.danger = dangerTypeList.get(pos).name;
        currentData.codeDanger = dangerTypeList.get(pos).code;
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

    private String[] getDataFromResource(Context context, int arrayRes) {
        return context.getResources().getStringArray(arrayRes);
    }
}
