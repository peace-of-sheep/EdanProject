package tech.ankainn.edanapplication.model.app.geninf;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class ExtraData {

    @ColumnInfo(name = "type_bsu")
    public String typeBSU = "";
    @ColumnInfo(name = "code_bsu")
    public String codeBSU = "";
    @ColumnInfo(name = "name_bsu")
    public String nameBSU = "";

    @ColumnInfo(name = "type_cm")
    public String typeCM = "";
    @ColumnInfo(name = "code_cm")
    public String codeCM = "";
    @ColumnInfo(name = "name_cm")
    public String nameCM = "";

    @ColumnInfo(name = "type_ca")
    public String typeCA = "";
    @ColumnInfo(name = "code_ca")
    public String codeCA = "";
    @ColumnInfo(name = "name_ca")
    public String nameCA = "";

    @ColumnInfo(name = "name_epd")
    public String nameEPD = "";

    /*@ColumnInfo(name = "number_id_sinpad")
    public String numberIdSinpad = "";*/
}
