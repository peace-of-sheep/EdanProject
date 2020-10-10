package tech.ankainn.edanapplication.model.app.formOne;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import tech.ankainn.edanapplication.db.EdanTypeConverters;
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;

@Entity(tableName = "form_one_table")
@TypeConverters(EdanTypeConverters.class)
public class FormOneData {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "form_one_id")
    public long id;

    @ColumnInfo(name = "form_one_api_id")
    public Integer formOneApiId = -1;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion = 0;

    @Embedded
    public GenInfData genInfData = new GenInfData();

    @ColumnInfo(name = "damage_one")
    public SelectableData damageOne = new SelectableData(5);
    @ColumnInfo(name = "damage_two")
    public SelectableData damageTwo = new SelectableData(5);
    @ColumnInfo(name = "damage_three")
    public SelectableData damageThree = new SelectableData(4);

    public SelectableData activities = new SelectableData(5);
    @ColumnInfo(name = "activities_others")
    public SelectableData activitiesOthers = new SelectableData(26);
    public SelectableData needs = new SelectableData(3);
    @ColumnInfo(name = "needs_others")
    public SelectableData needsOthers = new SelectableData(19);
}
