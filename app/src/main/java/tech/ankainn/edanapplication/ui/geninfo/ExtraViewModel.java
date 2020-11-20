package tech.ankainn.edanapplication.ui.geninfo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.model.app.geninf.ExtraData;
import tech.ankainn.edanapplication.repositories.GenInfRepository;

public class ExtraViewModel extends ViewModel {

    private LiveData<ExtraData> extraData;

    private ExtraData currentData;

    private final int arrayBSU = R.array.bsu;
    private final int arrayCA = R.array.ca;
    private final int arrayCM = R.array.cm;

    public ExtraViewModel(GenInfRepository genInfRepository) {
        LiveData<ExtraData> source = genInfRepository.loadExtraData();

        extraData = Transformations.map(source, extraData -> {
            this.currentData = extraData;
            return extraData;
        });
    }

    public LiveData<ExtraData> getExtraData() {
        return extraData;
    }

    /*public void setLocality(Context context, int pos) {
        String[] data = getDataFromRes(context, arrayLocalityRes);
        currentData.nameLocality = data[pos];
        currentData.codeLocality = Integer.toString(pos + 1);
    }*/

    public void setBSU(Context context, int pos) {
        String[] data = getDataFromRes(context, arrayBSU);
        currentData.typeBSU = data[pos];
        currentData.codeBSU = Integer.toString(pos + 1);
    }

    public void setCA(Context context, int pos) {
        String[] data = getDataFromRes(context, arrayCA);
        currentData.typeCA = data[pos];
        currentData.codeCA = Integer.toString(pos + 1);
    }

    public void setCM(Context context, int pos) {
        String[] data = getDataFromRes(context, arrayCM);
        currentData.typeCM = data[pos];
        currentData.codeCM = Integer.toString(pos + 1);
    }

    private String[] getDataFromRes(Context context, int arrayRes) {
        return context.getResources().getStringArray(arrayRes);
    }
}
