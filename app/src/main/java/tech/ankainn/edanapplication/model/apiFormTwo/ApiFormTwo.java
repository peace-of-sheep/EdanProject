package tech.ankainn.edanapplication.model.apiFormTwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiFormTwo {

    @SerializedName("form2a_cab")
    @Expose
    private FormTwoCab form2aCab;

    @SerializedName("informacion_vivienda")
    @Expose
    private InformacionVivienda informacionVivienda;

    @SerializedName("familias")
    @Expose
    private List<Familia> familias;

    public FormTwoCab getForm2aCab() {
        return form2aCab;
    }

    public void setForm2aCab(FormTwoCab form2aCab) {
        this.form2aCab = form2aCab;
    }

    public InformacionVivienda getInformacionVivienda() {
        return informacionVivienda;
    }

    public void setInformacionVivienda(InformacionVivienda informacionVivienda) {
        this.informacionVivienda = informacionVivienda;
    }

    public List<Familia> getFamilias() {
        return familias;
    }

    public void setFamilias(List<Familia> familias) {
        this.familias = familias;
    }
}
