package tech.ankainn.edanapplication.model.apiFormTwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Etario {

    @SerializedName("m")
    @Expose
    private Integer m;

    @SerializedName("f")
    @Expose
    private Integer f;

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

    public Integer getF() {
        return f;
    }

    public void setF(Integer f) {
        this.f = f;
    }
}
