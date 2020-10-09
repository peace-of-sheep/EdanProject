package tech.ankainn.edanapplication.model.app.formOne;

import androidx.room.ColumnInfo;

public class FormOneSubset {

    @ColumnInfo(name = "form_one_id")
    public long id;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion;

    public String department;
    public String province;
    public String district;

    public String date;
    public String hour;
}
