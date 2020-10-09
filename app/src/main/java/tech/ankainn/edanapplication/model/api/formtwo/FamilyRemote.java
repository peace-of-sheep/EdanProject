package tech.ankainn.edanapplication.model.api.formtwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FamilyRemote {

    @SerializedName("informacion_regular")
    @Expose
    private InformationRemote informacionRegular;

    public InformationRemote getInformacionRegular() {
        return informacionRegular;
    }

    public void setInformacionRegular(InformationRemote informacionRegular) {
        this.informacionRegular = informacionRegular;
    }
}
