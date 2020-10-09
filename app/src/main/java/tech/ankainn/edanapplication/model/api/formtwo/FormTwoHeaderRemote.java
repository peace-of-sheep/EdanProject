package tech.ankainn.edanapplication.model.api.formtwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormTwoHeaderRemote {

    @SerializedName("peligro_tipo")
    @Expose
    private Integer peligroTipo;
    @SerializedName("evaluacion_nro")
    @Expose
    private Integer evaluacionNro;
    @SerializedName("centro_poblado")
    @Expose
    private String centroPoblado;
    @SerializedName("caserio")
    @Expose
    private String caserio;
    @SerializedName("localidad")
    @Expose
    private String localidad;
    @SerializedName("calle")
    @Expose
    private String calle;
    @SerializedName("piso")
    @Expose
    private Integer piso;
    @SerializedName("empadronamiento_fecha_hora")
    @Expose
    private String empadronamientoFechaHora;
    @SerializedName("ocurrencia_fecha_hora")
    @Expose
    private String ocurrenciaFechaHora;

    public Integer getPeligroTipo() {
        return peligroTipo;
    }

    public void setPeligroTipo(Integer peligroTipo) {
        this.peligroTipo = peligroTipo;
    }

    public Integer getEvaluacionNro() {
        return evaluacionNro;
    }

    public void setEvaluacionNro(Integer evaluacionNro) {
        this.evaluacionNro = evaluacionNro;
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

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
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
}
