package tech.ankainn.edanapplication.model.api.formtwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HouseholdRemote {

    @SerializedName("condicion_vivienda_post_desastre")
    @Expose
    private Integer condicionViviendaPostDesastre;
    @SerializedName("condicion_uso_instalacion")
    @Expose
    private Integer condicionUsoInstalacion;
    @SerializedName("lote_nro")
    @Expose
    private String loteNro;
    @SerializedName("tenencia_propia")
    @Expose
    private String tenenciaPropia;
    @SerializedName("tipo_material_vivienda")
    @Expose
    private MaterialTypeRemote tipoMaterialVivienda;

    public Integer getCondicionViviendaPostDesastre() {
        return condicionViviendaPostDesastre;
    }

    public void setCondicionViviendaPostDesastre(Integer condicionViviendaPostDesastre) {
        this.condicionViviendaPostDesastre = condicionViviendaPostDesastre;
    }

    public Integer getCondicionUsoInstalacion() {
        return condicionUsoInstalacion;
    }

    public void setCondicionUsoInstalacion(Integer condicionUsoInstalacion) {
        this.condicionUsoInstalacion = condicionUsoInstalacion;
    }

    public String getLoteNro() {
        return loteNro;
    }

    public void setLoteNro(String loteNro) {
        this.loteNro = loteNro;
    }

    public String getTenenciaPropia() {
        return tenenciaPropia;
    }

    public void setTenenciaPropia(String tenenciaPropia) {
        this.tenenciaPropia = tenenciaPropia;
    }

    public MaterialTypeRemote getTipoMaterialVivienda() {
        return tipoMaterialVivienda;
    }

    public void setTipoMaterialVivienda(MaterialTypeRemote tipoMaterialVivienda) {
        this.tipoMaterialVivienda = tipoMaterialVivienda;
    }
}
