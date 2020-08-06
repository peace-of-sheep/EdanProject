package tech.ankainn.edanapplication.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataResponse {

    @SerializedName("FORM2A_CAB_ID")
    @Expose
    private Integer fORM2ACABID;

    public Integer getFORM2ACABID() {
        return fORM2ACABID;
    }

    public void setFORM2ACABID(Integer fORM2ACABID) {
        this.fORM2ACABID = fORM2ACABID;
    }
}
