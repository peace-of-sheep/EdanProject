package tech.ankainn.edanapplication.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalInjury {

    @SerializedName("lesionados")
    @Expose
    private Integer lesionados;

    @SerializedName("desaparecidos")
    @Expose
    private Integer desaparecidos;

    @SerializedName("fallecidos")
    @Expose
    private Integer fallecidos;

    public Integer getLesionados() {
        return lesionados;
    }

    public void setLesionados(Integer lesionados) {
        this.lesionados = lesionados;
    }

    public Integer getDesaparecidos() {
        return desaparecidos;
    }

    public void setDesaparecidos(Integer desaparecidos) {
        this.desaparecidos = desaparecidos;
    }

    public Integer getFallecidos() {
        return fallecidos;
    }

    public void setFallecidos(Integer fallecidos) {
        this.fallecidos = fallecidos;
    }
}
