package tech.ankainn.edanapplication.model.apiFormTwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InformacionEspecial {

    @SerializedName("grupos_vulnerables")
    @Expose
    private GruposVulnerables gruposVulnerables;

    public GruposVulnerables getGruposVulnerables() {
        return gruposVulnerables;
    }

    public void setGruposVulnerables(GruposVulnerables gruposVulnerables) {
        this.gruposVulnerables = gruposVulnerables;
    }
}
