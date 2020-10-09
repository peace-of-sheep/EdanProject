package tech.ankainn.edanapplication.model.dto;

import androidx.room.ColumnInfo;

public class FormTwoSubset {

    @ColumnInfo(name = "form_two_id")
    public long id;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion;

    public String department;
    public String province;
    public String district;

    public String lot;

    @ColumnInfo(name = "date_event")
    public String date;
    @ColumnInfo(name = "hour_event")
    public String hour;

    @ColumnInfo(name = "form_two_api_id")
    public Integer formTwoApiId;
}
