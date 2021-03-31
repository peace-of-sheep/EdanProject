package tech.ankainn.edanapplication.model.api.ubigeo;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeptEntity {

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("prov")
    @Expose
    @Ignore
    public List<ProvEntity> prov = null;
}
