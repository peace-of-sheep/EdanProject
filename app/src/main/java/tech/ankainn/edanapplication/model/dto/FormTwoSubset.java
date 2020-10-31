package tech.ankainn.edanapplication.model.dto;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import com.google.gson.Gson;

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

    @ColumnInfo(name = "date_creation")
    public String dateCreation;
    @ColumnInfo(name = "hour_creation")
    public String hourCreation;

    @ColumnInfo(name = "form_two_api_id")
    public Integer formTwoApiId;

    @Ignore
    public boolean loading = false;

    @Override
    public String toString() {
        String hash = Integer.toHexString(this.hashCode());
        return "{"+hash+"}{"+formTwoApiId+"}{"+loading+"}";
    }
}
