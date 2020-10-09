package tech.ankainn.edanapplication.model.app.formTwo;

import androidx.room.ColumnInfo;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class HouseholdData {

    public String lot = "";
    public Boolean owner = false;

    public String useHouse = "";
    public Integer codeUseHouse = -1;

    public String conditionHouse = "";
    public Integer codeConditionHouse = -1;

    public String typeRoof = "";
    public Integer codeRoof = -1;
    public String typeWall = "";
    public Integer codeWall = -1;
    public String typeFloor = "";
    public Integer codeFloor = -1;

    @NotNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
