package tech.ankainn.edanapplication.model.app.ubigeo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalEntity {

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("name")
    @Expose
    public String name;
}
