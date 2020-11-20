package tech.ankainn.edanapplication.model.app.geninf;

import androidx.room.ColumnInfo;

public class HeaderData {

    @ColumnInfo(name = "group_danger")
    public String groupDanger = "";
    @ColumnInfo(name = "code_group_danger")
    public String codeGroupDanger = "";
    @ColumnInfo(name = "type_danger")
    public String danger = "";
    @ColumnInfo(name = "code_type_danger")
    public String codeDanger = "";

    public String department = "";
    @ColumnInfo(name = "code_department")
    public String codeDepartment = "";
    public String province = "";
    @ColumnInfo(name = "code_province")
    public String codeProvince = "";
    public String district = "";
    @ColumnInfo(name = "code_district")
    public String codeDistrict = "";
    public String locality = "";
    @ColumnInfo(name = "code_locality")
    public String codeLocality = "";

    @ColumnInfo(name = "date_event")
    public String dateEvent = "";
    @ColumnInfo(name = "hour_event")
    public String hourEvent = "";

    @ColumnInfo(name = "date_creation")
    public String dateCreation = "";
    @ColumnInfo(name = "hour_creation")
    public String hourCreation = "";
}
