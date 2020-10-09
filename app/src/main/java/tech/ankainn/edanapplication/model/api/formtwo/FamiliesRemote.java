package tech.ankainn.edanapplication.model.api.formtwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FamiliesRemote {

    @SerializedName("0")
    @Expose
    private FamilyRemote family;

    public FamilyRemote getFamily() {
        return family;
    }

    public void setFamily(FamilyRemote family) {
        this.family = family;
    }
}
