package tech.ankainn.edanapplication.model.app.formTwo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "livelihood_table")
public class LivelihoodData {

    @Ignore
    public long tempId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "livelihood_id")
    public long id;

    @ColumnInfo(name = "form_two_owner_id")
    public long formTwoOwnerId;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion = 0;

    public String name = "";
    public String type = "";
    @ColumnInfo(name = "amount_lost")
    public Integer amountLost;
    @ColumnInfo(name = "amount_affected")
    public Integer amountAffected;

    @NotNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
