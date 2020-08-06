package tech.ankainn.edanapplication.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InformacionRegular {

    @SerializedName("responsable")
    @Expose
    private Responsable responsable;

    @SerializedName("vida_salud")
    @Expose
    private LifeHealth vidaSalud;

    @SerializedName("grupos_etarios")
    @Expose
    private GrupoEtarios gruposEtarios;

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public LifeHealth getVidaSalud() {
        return vidaSalud;
    }

    public void setVidaSalud(LifeHealth vidaSalud) {
        this.vidaSalud = vidaSalud;
    }

    public GrupoEtarios getGruposEtarios() {
        return gruposEtarios;
    }

    public void setGruposEtarios(GrupoEtarios gruposEtarios) {
        this.gruposEtarios = gruposEtarios;
    }
}
