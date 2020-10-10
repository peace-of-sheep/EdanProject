package tech.ankainn.edanapplication.model.app.formTwo;

import androidx.room.ColumnInfo;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class HouseholdData {

    public String lot = "";

    public Boolean owner = false;

    @ColumnInfo(name = "use_house")
    public String useHouse = "";
    @ColumnInfo(name = "code_use_house")
    public Integer codeUseHouse = -1;

    @ColumnInfo(name = "condition_house")
    public String conditionHouse = "";
    @ColumnInfo(name = "code_condition_house")
    public Integer codeConditionHouse = -1;

    @ColumnInfo(name = "type_roof")
    public String typeRoof = "";
    @ColumnInfo(name = "code_roof")
    public Integer codeRoof = -1;
    @ColumnInfo(name = "type_wall")
    public String typeWall = "";
    @ColumnInfo(name = "code_wall")
    public Integer codeWall = -1;
    @ColumnInfo(name = "type_floor")
    public String typeFloor = "";
    @ColumnInfo(name = "code_floor")
    public Integer codeFloor = -1;

    @NotNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
