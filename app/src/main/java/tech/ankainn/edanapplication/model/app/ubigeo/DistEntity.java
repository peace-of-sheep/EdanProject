package tech.ankainn.edanapplication.model.app.ubigeo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistEntity {

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("local")
    @Expose
    public List<LocalEntity> local = null;
}
