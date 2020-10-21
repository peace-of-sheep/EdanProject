package tech.ankainn.edanapplication.model.app.ubigeo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "ubigeo_table")
public class UbigeoLocation {

    @PrimaryKey
    @SerializedName("ID")
    @Expose
    @NotNull
    public String code;

    @ColumnInfo(name = "owner_code")
    public String ownerCode;

    @SerializedName("TEXT")
    @Expose
    public String name;

    @Override
    @NotNull
    public String toString() {
        return new Gson().toJson(this);
    }
}
