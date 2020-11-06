package tech.ankainn.edanapplication.model.dto;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class FormOneSubset {

    @ColumnInfo(name = "form_one_id")
    public long id;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion;

    public String department;
    public String province;
    public String district;

    @ColumnInfo(name = "date_event")
    public String dateEvent;
    @ColumnInfo(name = "hour_event")
    public String hourEvent;

    @Ignore
    public boolean loading = false;
}
