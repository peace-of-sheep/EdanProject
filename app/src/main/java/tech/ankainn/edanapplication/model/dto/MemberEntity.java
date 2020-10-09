package tech.ankainn.edanapplication.model.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "member_table")
public class MemberEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "member_id")
    public long memberId;

    @ColumnInfo(name = "form_two_owner_id")
    public long formTwoOwnerId;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion;

    @ColumnInfo(name = "type_identification")
    public String typeIdentification;
    @ColumnInfo(name = "code_identification")
    public Integer codeIdentification;
    @ColumnInfo(name = "text_identification")
    public Integer textIdentification;

    public String surname;
    public String name;
    public String birthdate;
    public Integer age;
    public String gender;
    @ColumnInfo(name = "code_gender")
    public String codeGender;


    public String condition;
    @ColumnInfo(name = "code_condition")
    public String codeCondition = "";
    @ColumnInfo(name = "personal_injury")
    public String personalInjury;
    @ColumnInfo(name = "code_personal_injury")
    public String codePersonalInjury = "";
}
