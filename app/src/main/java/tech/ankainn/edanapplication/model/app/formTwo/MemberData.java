package tech.ankainn.edanapplication.model.app.formTwo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "member_table")
public class MemberData {

    @Ignore
    public long tempId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "member_id")
    public long id;

    @ColumnInfo(name = "form_two_owner_id")
    public long formTwoOwnerId;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion = 0;

    @ColumnInfo(name = "type_identification")
    public String typeIdentification = "";
    @ColumnInfo(name = "code_identification")
    public Integer codeIdentification = -1;
    @ColumnInfo(name = "text_identification")
    public Integer textIdentification;

    public String surname = "";
    public String name = "";
    public String birthdate = "";
    public Integer age;
    public String gender = "";
    @ColumnInfo(name = "code_gender")
    public String codeGender = "";
    public String condition = "";
    @ColumnInfo(name = "code_condition")
    public String codeCondition = "";
    @ColumnInfo(name = "personal_injury")
    public String personalInjury = "";
    @ColumnInfo(name = "code_personal_injury")
    public String codePersonalInjury = "";

    public boolean notEmpty() {
        return checkValue(surname) && checkValue(name) && checkValue(birthdate);
    }

    private boolean checkValue(String value) {
        return value != null && value.length() > 0;
    }

    @NotNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
