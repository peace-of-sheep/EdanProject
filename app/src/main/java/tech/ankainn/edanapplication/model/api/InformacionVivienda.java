package tech.ankainn.edanapplication.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InformacionVivienda {

    @SerializedName("lote_nro")
    @Expose
    private Integer loteNro;

    @SerializedName("tenencia_propia")
    @Expose
    private String tenenciaPropia;

    @SerializedName("condicion_uso_instalacion")
    @Expose
    private String condicionUsoInstalacion;

    @SerializedName("condicion_vivienda_post_desastre")
    @Expose
    private String condicionViviendaPostDesastre;

    @SerializedName("tipo_material_vivienda")
    @Expose
    private MaterialType tipoMaterialVivienda;

    public Integer getLoteNro() {
        return loteNro;
    }

    public void setLoteNro(Integer loteNro) {
        this.loteNro = loteNro;
    }

    public String getTenenciaPropia() {
        return tenenciaPropia;
    }

    public void setTenenciaPropia(String tenenciaPropia) {
        this.tenenciaPropia = tenenciaPropia;
    }

    public String getCondicionUsoInstalacion() {
        return condicionUsoInstalacion;
    }

    public void setCondicionUsoInstalacion(String condicionUsoInstalacion) {
        this.condicionUsoInstalacion = condicionUsoInstalacion;
    }

    public String getCondicionViviendaPostDesastre() {
        return condicionViviendaPostDesastre;
    }

    public void setCondicionViviendaPostDesastre(String condicionViviendaPostDesastre) {
        this.condicionViviendaPostDesastre = condicionViviendaPostDesastre;
    }

    public MaterialType getTipoMaterialVivienda() {
        return tipoMaterialVivienda;
    }

    public void setTipoMaterialVivienda(MaterialType tipoMaterialVivienda) {
        this.tipoMaterialVivienda = tipoMaterialVivienda;
    }
}
