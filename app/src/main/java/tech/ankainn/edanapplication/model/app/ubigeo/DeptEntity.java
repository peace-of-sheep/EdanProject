package tech.ankainn.edanapplication.model.app.ubigeo;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
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
