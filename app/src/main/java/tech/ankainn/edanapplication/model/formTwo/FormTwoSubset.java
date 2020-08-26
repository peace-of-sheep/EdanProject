package tech.ankainn.edanapplication.model.formTwo;

import androidx.room.ColumnInfo;

public class FormTwoSubset {

    @ColumnInfo(name = "form_two_id")
    public long id;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion;

    public String department;
    public String province;
    public String district;

    public String address;

    public String lot;

    public String date;
    public String hour;
}
