package tech.ankainn.edanapplication.model.app.formTwo;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.app.geninf.GenInfData;

@Entity(tableName = "form_two_table")
public class FormTwoData {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "form_two_id")
    public long id;

    @ColumnInfo(name = "form_two_api_id")
    public Integer formTwoApiId = -1;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion = 0;

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
