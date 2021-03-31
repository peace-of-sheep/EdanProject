package tech.ankainn.edanapplication.model.api.livelihood;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LivelihoodTypeRemote {

    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("name")
    @Expose
    public String name;
}
