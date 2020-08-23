package tech.ankainn.edanapplication.model.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import tech.ankainn.edanapplication.db.EdanTypeConverters;

@Entity(tableName = "form_one_table")
@TypeConverters(EdanTypeConverters.class)
public class FormOneEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "form_one_id")
    public long formOneId;

    @ColumnInfo(name = "form_one_api_id")
    public Integer formOneApiId;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion;

    public Double latitude;
    public Double longitude;
    public Integer altitude;
    public String transport;
    public String reference;

    @ColumnInfo(name = "group_danger")
    public String groupDanger;
    @ColumnInfo(name = "type_danger")
    public String typeDanger;
    public String date;
    public String hour;
    public String department;
    public String province;
    public String district;
    public String locality;
    public String zone;

    public List<Integer> damageOneQuantity;
    public List<Boolean> damageOneBool;
    public List<Boolean> damageTwoBool;
    public List<Boolean> damageThreeBool;

    public List<Boolean> activitiesBool;
    public String activitiesObservation;

    public List<Boolean> needsBool;
    public String needsObservation;
}
