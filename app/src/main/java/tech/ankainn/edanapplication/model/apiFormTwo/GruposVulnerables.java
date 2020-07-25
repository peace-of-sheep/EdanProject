package tech.ankainn.edanapplication.model.apiFormTwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GruposVulnerables {

    @SerializedName("gestantes")
    @Expose
    private Integer gestantes;

    @SerializedName("personas_con_discapacidad")
    @Expose
    private Integer personasConDiscapacidad;

    @SerializedName("tipo_enfermedad_cronica")
    @Expose
    private Integer tipoEnfermedadCronica;

    public Integer getGestantes() {
        return gestantes;
    }

    public void setGestantes(Integer gestantes) {
        this.gestantes = gestantes;
    }

    public Integer getPersonasConDiscapacidad() {
        return personasConDiscapacidad;
    }

    public void setPersonasConDiscapacidad(Integer personasConDiscapacidad) {
        this.personasConDiscapacidad = personasConDiscapacidad;
    }

    public Integer getTipoEnfermedadCronica() {
        return tipoEnfermedadCronica;
    }

    public void setTipoEnfermedadCronica(Integer tipoEnfermedadCronica) {
        this.tipoEnfermedadCronica = tipoEnfermedadCronica;
    }
}
