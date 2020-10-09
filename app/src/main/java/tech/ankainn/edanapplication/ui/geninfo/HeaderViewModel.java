package tech.ankainn.edanapplication.ui.geninfo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.binding.Converter;
import tech.ankainn.edanapplication.model.app.geninf.HeaderData;
import tech.ankainn.edanapplication.repositories.GenInfRepository;

public class HeaderViewModel extends ViewModel {

    private GenInfRepository genInfRepository;

    private LiveData<HeaderData> headerData;
    private HeaderData currentData;

    private final int arrayGroupRes = R.array.group_danger;
    private final int arrayDangerRes = R.array.type_danger;
    private final int arrayDepartmentRes = R.array.departments;

    public HeaderViewModel(GenInfRepository genInfRepository) {
        this.genInfRepository = genInfRepository;

        LiveData<HeaderData> source = genInfRepository.loadHeaderData();
        headerData = Transformations.map(source, headerData -> {
            this.currentData = headerData;
            return headerData;
        });
    }

    public LiveData<HeaderData> getHeaderData() {
        return headerData;
    }

    public void setDangerGroup(Context context, int pos) {
        String[] data = getDataFromResource(context, arrayGroupRes);
        currentData.groupDanger = data[pos];
        currentData.codeGroupDanger = pos + 1;
    }

    public void setDanger(Context context, int pos) {
        String[] data = getDataFromResource(context, arrayDangerRes);
        currentData.danger = data[pos];
        currentData.codeDanger = pos + 1;
    }

    public void setDepartment(Context context, int pos) {
        String[] data = getDataFromResource(context, arrayDepartmentRes);
        currentData.department = data[pos];
        currentData.codeDepartment = Integer.toString(pos);
    }

    public void setProvince(Context context, int pos) {
        String[] data = getDataFromResource(context, arrayDepartmentRes);
        currentData.province = data[pos];
        currentData.codeProvince = Integer.toString(pos);
    }

    public void setDistrict(Context context, int pos) {
        String[] data = getDataFromResource(context, arrayDepartmentRes);
        currentData.district = data[pos];
        currentData.codeDistrict = Integer.toString(pos);
    }

    private String[] getDataFromResource(Context context, int arrayRes) {
        return context.getResources().getStringArray(arrayRes);
    }
}
