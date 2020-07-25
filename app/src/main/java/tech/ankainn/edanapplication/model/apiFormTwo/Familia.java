package tech.ankainn.edanapplication.model.apiFormTwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Familia {

    @SerializedName("informacion_regular")
    @Expose
    private InformacionRegular informacionRegular;

    @SerializedName("informacion_especial")
    @Expose
    private InformacionEspecial informacionEspecial;

    public InformacionRegular getInformacionRegular() {
        return informacionRegular;
    }

    public void setInformacionRegular(InformacionRegular informacionRegular) {
        this.informacionRegular = informacionRegular;
    }

    public InformacionEspecial getInformacionEspecial() {
        return informacionEspecial;
    }

    public void setInformacionEspecial(InformacionEspecial informacionEspecial) {
        this.informacionEspecial = informacionEspecial;
    }
}
