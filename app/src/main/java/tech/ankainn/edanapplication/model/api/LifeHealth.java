package tech.ankainn.edanapplication.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LifeHealth {

    @SerializedName("condicion")
    @Expose
    private Condition condicion;

    @SerializedName("danios_personales")
    @Expose
    private PersonalInjury daniosPersonales;

    public Condition getCondicion() {
        return condicion;
    }

    public void setCondicion(Condition condicion) {
        this.condicion = condicion;
    }

    public PersonalInjury getDaniosPersonales() {
        return daniosPersonales;
    }

    public void setDaniosPersonales(PersonalInjury daniosPersonales) {
        this.daniosPersonales = daniosPersonales;
    }
}
