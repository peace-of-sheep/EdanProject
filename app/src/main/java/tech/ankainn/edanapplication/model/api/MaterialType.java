package tech.ankainn.edanapplication.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialType {

    @SerializedName("techo")
    @Expose
    private Integer techo;

    @SerializedName("pared")
    @Expose
    private Integer pared;

    @SerializedName("piso")
    @Expose
    private Integer piso;

    public Integer getTecho() {
        return techo;
    }

    public void setTecho(Integer techo) {
        this.techo = techo;
    }

    public Integer getPared() {
        return pared;
    }

    public void setPared(Integer pared) {
        this.pared = pared;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }
}
