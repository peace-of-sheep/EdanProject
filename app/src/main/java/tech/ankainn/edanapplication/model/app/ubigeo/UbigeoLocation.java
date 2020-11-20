package tech.ankainn.edanapplication.model.app.ubigeo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import tech.ankainn.edanapplication.model.app.auth.UserData;

@Entity(tableName = "ubigeo_table")
public class UbigeoLocation {

    @SerializedName("code")
    @Expose
    @PrimaryKey
    @NotNull
    public String code = "";

    @SerializedName("name")
    @Expose
    public String name;

    @ColumnInfo(name = "owner_code")
    public String ownerCode;

    @Override
    @NotNull
    public String toString() {
        return name;
    }

    public String dump() {
        return new Gson().toJson(this);
    }
}
