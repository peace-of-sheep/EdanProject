package tech.ankainn.edanapplication.model.api.formtwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsableRemote {

    @SerializedName("apellidos")
    @Expose
    private String apellidos;
    @SerializedName("nombres")
    @Expose
    private String nombres;
    @SerializedName("edad")
    @Expose
    private String edad;
    @SerializedName("documento")
    @Expose
    private DocumentRemote documento;

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public DocumentRemote getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentRemote documento) {
        this.documento = documento;
    }
}
