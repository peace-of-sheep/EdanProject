package tech.ankainn.edanapplication.model.app.formTwo;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
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
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;

@Entity(foreignKeys = @ForeignKey(entity = UserData.class,
                                  parentColumns = "user_id",
                                  childColumns = "user_owner_id",
                                  onDelete = ForeignKey.CASCADE),
        indices = @Index(value = "user_owner_id"),
        tableName = "form_two_table")
public class FormTwoData {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "form_two_id")
    public long id;

    @ColumnInfo(name = "user_owner_id")
    public long ownerUserId;

    @ColumnInfo(name = "form_two_api_id")
    public Integer formTwoApiId = -1;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion = 0;

    public String username = "";

    @Embedded
    public GenInfData genInfData = new GenInfData();

    @Embedded
    public HouseholdData householdData = new HouseholdData();

    @Ignore
    public int memberDataCount = 0;

    @Ignore
    public List<MemberData> memberDataList = new ArrayList<>();

    @NotNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
