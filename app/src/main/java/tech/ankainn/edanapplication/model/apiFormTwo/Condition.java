package tech.ankainn.edanapplication.model.apiFormTwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Condition {

    @SerializedName("damnificados")
    @Expose
    private Integer damnificados;

    @SerializedName("afectados")
    @Expose
    private Integer afectados;

    public Integer getDamnificados() {
        return damnificados;
    }

    public void setDamnificados(Integer damnificados) {
        this.damnificados = damnificados;
    }

    public Integer getAfectados() {
        return afectados;
    }

    public void setAfectados(Integer afectados) {
        this.afectados = afectados;
    }
}
