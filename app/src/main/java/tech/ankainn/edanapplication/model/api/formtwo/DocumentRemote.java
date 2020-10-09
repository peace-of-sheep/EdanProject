package tech.ankainn.edanapplication.model.api.formtwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentRemote {
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("nro")
    @Expose
    private String nro;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }
}
