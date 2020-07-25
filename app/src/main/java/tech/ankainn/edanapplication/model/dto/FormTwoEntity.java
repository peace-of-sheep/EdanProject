package tech.ankainn.edanapplication.model.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "form_two_table")
public class FormTwoEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "form_two_id")
    public int formTwoId;

    @ColumnInfo(name = "data_version")
    public int dataVersion;

    @ColumnInfo(name = "form_two_api_id")
    public Integer formTwoApiId;
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
    public Double latitude;
    public Double longitude;
    public Integer altitude;
    public String transport;
    public String reference;
    @ColumnInfo(name = "lot")
    public String lot;
    public Boolean owner;
    public String condition;
    public String roof;
    @ColumnInfo(name = "id_roof")
    public Integer idRoof;
    public String wall;
    @ColumnInfo(name = "id_wall")
    public Integer idWall;
    public String floor;
    @ColumnInfo(name = "id_floor")
    public Integer idFloor;
}
