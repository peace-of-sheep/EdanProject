package tech.ankainn.edanapplication.model.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "member_table")
public class MemberEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "member_id")
    public int memberId;

    public int formTwoId;

    public Boolean head;
    public String name;
    public Integer age;
    public String gender;
    @ColumnInfo(name = "id_type")
    public String idType;
    @ColumnInfo(name = "id_number")
    public Integer idNumber;
    public String condition;
    @ColumnInfo(name = "personal_injury")
    public String personalInjury;
}
