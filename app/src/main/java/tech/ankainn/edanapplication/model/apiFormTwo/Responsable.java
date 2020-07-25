package tech.ankainn.edanapplication.model.apiFormTwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responsable {

    @SerializedName("apellidos")
    @Expose
    private String apellidos;

    @SerializedName("nombres")
    @Expose
    private String nombres;

    @SerializedName("edad")
    @Expose
    private Integer edad;

    @SerializedName("documento")
    @Expose
    private Document document;

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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
