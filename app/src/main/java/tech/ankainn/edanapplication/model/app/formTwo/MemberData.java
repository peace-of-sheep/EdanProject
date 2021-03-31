package tech.ankainn.edanapplication.model.app.formTwo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.app.auth.UserData;

@Entity(foreignKeys = @ForeignKey(entity = FormTwoData.class,
                                parentColumns = "form_two_id",
                                childColumns = "form_two_owner_id",
                                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "form_two_owner_id")},
        tableName = "member_table")
public class MemberData {

    @Ignore
    public long tempId;

    @Ignore
    public boolean toRemove = false;

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

    @ColumnInfo(name = "text_injury_severity")
    public String textInjurySeverity = "";
    @ColumnInfo(name = "code_injury_severity")
    public String codeInjurySeverity = "";


    @ColumnInfo(name = "livelihood_owner")
    public Boolean livelihoodOwner = false;

    public Boolean pregnant = false;

    @ColumnInfo(name = "pregnant_time")
    public Integer pregnantTime;

    public String disability = "";

    @ColumnInfo(name = "chronic_disease")
    public String chronicDisease = "";

    @Ignore
    public int livelihoodDataCount = 0;

    @Ignore
    public List<LivelihoodData> livelihoodDataList = new ArrayList<>();

    public boolean notEmpty() {
        return checkValue(surname) && checkValue(name) && checkValue(birthdate);
    }

    private boolean checkValue(String value) {
        return value != null && value.length() > 0;
    }

    @NotNull
    @Override
    public String toString() {
        return name + " " + surname;
    }
}
