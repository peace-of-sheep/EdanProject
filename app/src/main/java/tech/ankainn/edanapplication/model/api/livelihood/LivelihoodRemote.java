package tech.ankainn.edanapplication.model.api.livelihood;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LivelihoodRemote {
    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("type")
    @Expose
    public List<LivelihoodTypeRemote> type = null;
}
