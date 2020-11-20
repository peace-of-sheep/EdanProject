package tech.ankainn.edanapplication.model.app.ubigeo;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProvEntity {

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("dist")
    @Expose
    public List<DistEntity> dist = null;
}
