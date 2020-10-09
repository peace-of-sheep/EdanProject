package tech.ankainn.edanapplication.model.api.formtwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InformationRemote {
    @SerializedName("responsable")
    @Expose
    private ResponsableRemote responsable;

    public ResponsableRemote getResponsable() {
        return responsable;
    }

    public void setResponsable(ResponsableRemote responsable) {
        this.responsable = responsable;
    }
}
