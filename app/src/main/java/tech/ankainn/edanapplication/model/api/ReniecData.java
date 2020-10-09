package tech.ankainn.edanapplication.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReniecData {

    @SerializedName("apellidos")
    @Expose
    public String apellidos;

    @SerializedName("nombres")
    @Expose
    public String nombres;

    @SerializedName("edad")
    @Expose
    public String edad;

    @SerializedName("fecNacimiento")
    @Expose
    public String birthdate;

    @SerializedName("tipoSexo")
    @Expose
    public String tipoSexo;

    @Override
    public String toString() {
        return "PersonReniecData{" +
                "apellidos='" + apellidos + '\'' +
                ", nombres='" + nombres + '\'' +
                ", edad='" + edad + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", tipoSexo='" + tipoSexo + '\'' +
                '}';
    }
}
