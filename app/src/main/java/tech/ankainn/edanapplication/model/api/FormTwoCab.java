package tech.ankainn.edanapplication.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormTwoCab {

    @SerializedName("evaluacion_nro")
    @Expose
    private String evaluacionNro;

    @SerializedName("sinpad_nro")
    @Expose
    private String sinpadNro;

    @SerializedName("peligro_tipo")
    @Expose
    private String peligroTipo;

    @SerializedName("empadronamiento_fecha_hora")
    @Expose
    private String empadronamientoFechaHora;

    @SerializedName("ocurrencia_fecha_hora")
    @Expose
    private String ocurrenciaFechaHora;

    @SerializedName("departamento")
    @Expose
    private String departamento;

    @SerializedName("provincia")
    @Expose
    private String provincia;

    @SerializedName("distrito")
    @Expose
    private String distrito;

    @SerializedName("localidad")
    @Expose
    private String localidad;

    @SerializedName("sector")
    @Expose
    private String sector;

    @SerializedName("calle")
    @Expose
    private String calle;

    @SerializedName("piso")
    @Expose
    private String piso;

    @SerializedName("centro_poblado")
    @Expose
    private String centroPoblado;

    @SerializedName("caserio")
    @Expose
    private String caserio;

    @SerializedName("anexo")
    @Expose
    private String anexo;

    @SerializedName("hoja_nro")
    @Expose
    private String hojaNro;

    @SerializedName("otros")
    @Expose
    private String otros;

    public String getEvaluacionNro() {
        return evaluacionNro;
    }

    public void setEvaluacionNro(String evaluacionNro) {
        this.evaluacionNro = evaluacionNro;
    }

    public String getSinpadNro() {
        return sinpadNro;
    }

    public void setSinpadNro(String sinpadNro) {
        this.sinpadNro = sinpadNro;
    }

    public String getPeligroTipo() {
        return peligroTipo;
    }

    public void setPeligroTipo(String peligroTipo) {
        this.peligroTipo = peligroTipo;
    }

    public String getEmpadronamientoFechaHora() {
        return empadronamientoFechaHora;
    }

    public void setEmpadronamientoFechaHora(String empadronamientoFechaHora) {
        this.empadronamientoFechaHora = empadronamientoFechaHora;
    }

    public String getOcurrenciaFechaHora() {
        return ocurrenciaFechaHora;
    }

    public void setOcurrenciaFechaHora(String ocurrenciaFechaHora) {
        this.ocurrenciaFechaHora = ocurrenciaFechaHora;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public String getCaserio() {
        return caserio;
    }

    public void setCaserio(String caserio) {
        this.caserio = caserio;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public String getHojaNro() {
        return hojaNro;
    }

    public void setHojaNro(String hojaNro) {
        this.hojaNro = hojaNro;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }
}
