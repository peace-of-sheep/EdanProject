package tech.ankainn.edanapplication.model.api.formtwo;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class FormTwoRemote {

    @SerializedName("form2a_cab")
    @Expose
    public FormTwoHeaderRemote form2aCab;
    @SerializedName("informacion_vivienda")
    @Expose
    public HouseholdRemote informacionVivienda;
    @SerializedName("familias")
    @Expose
    public FamiliesRemote familias;

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

    @Override
    @NotNull
    public String toString() {
        return new Gson().toJson(this);
    }
}
