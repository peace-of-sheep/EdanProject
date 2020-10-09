package tech.ankainn.edanapplication.model.api.formtwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormTwoRemote {

    @SerializedName("form2a_cab")
    @Expose
    private FormTwoHeaderRemote form2aCab;
    @SerializedName("informacion_vivienda")
    @Expose
    private HouseholdRemote informacionVivienda;
    @SerializedName("familias")
    @Expose
    private FamiliesRemote familias;

    public FormTwoHeaderRemote getForm2aCab() {
        return form2aCab;
    }

    public void setForm2aCab(FormTwoHeaderRemote form2aCab) {
        this.form2aCab = form2aCab;
    }

    public HouseholdRemote getInformacionVivienda() {
        return informacionVivienda;
    }

    public void setInformacionVivienda(HouseholdRemote informacionVivienda) {
        this.informacionVivienda = informacionVivienda;
    }

    public FamiliesRemote getFamilias() {
        return familias;
    }

    public void setFamilias(FamiliesRemote familias) {
        this.familias = familias;
    }
}
