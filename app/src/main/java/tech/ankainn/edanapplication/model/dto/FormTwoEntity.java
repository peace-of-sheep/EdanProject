package tech.ankainn.edanapplication.model.dto;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "form_two_table")
public class FormTwoEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "form_two_id")
    public long formTwoId;

    @ColumnInfo(name = "form_two_api_id")
    public Integer formTwoApiId;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion;

    @Embedded
    public HeaderEntity headerEntity;

    public String locality;
    @ColumnInfo(name = "code_locality")
    public String codeLocality;

    @ColumnInfo(name = "type_bsu")
    public String typeBSU;
    @ColumnInfo(name = "code_bsu")
    public String codeBSU;
    @ColumnInfo(name = "text_bsu")
    public String textBSU;
    @ColumnInfo(name = "type_cm")
    public String typeCM;
    @ColumnInfo(name = "code_cm")
    public String codeCM;
    @ColumnInfo(name = "text_cm")
    public String textCM;
    @ColumnInfo(name = "type_ca")
    public String typeCA;
    @ColumnInfo(name = "code_ca")
    public String codeCA;
    @ColumnInfo(name = "text_ca")
    public String textCA;
    @ColumnInfo(name = "text_epd")
    public String textEPD;

    public Double latitude;
    public Double longitude;

    public String lot;

    public Boolean owner;

    @ColumnInfo(name = "use_house")
    public String useHouse;
    @ColumnInfo(name = "code_use_house")
    public Integer codeUseHouse;

    @ColumnInfo(name = "condition_house")
    public String conditionHouse;
    @ColumnInfo(name = "code_condition_house")
    public Integer codeConditionHouse;

    @ColumnInfo(name = "type_roof")
    public String typeRoof;
    @ColumnInfo(name = "code_roof")
    public Integer codeRoof;
    @ColumnInfo(name = "type_wall")
    public String typeWall;
    @ColumnInfo(name = "code_wall")
    public Integer codeWall;
    @ColumnInfo(name = "type_floor")
    public String typeFloor;
    @ColumnInfo(name = "code_floor")
    public Integer codeFloor;
}
