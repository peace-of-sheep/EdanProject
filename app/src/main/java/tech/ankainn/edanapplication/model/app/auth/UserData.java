package tech.ankainn.edanapplication.model.app.auth;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "user_table")
public class UserData {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public long id;

    public String hash = "";

    public String surname = "";
    public String name = "";
    public String email = "";
    @ColumnInfo(name = "identification")
    public String idenNum = "";

    @Ignore
    public boolean online = false;
    @Ignore
    public String token = null;

    @Override
    @NotNull
    public String toString() {
        return new Gson().toJson(this);
    }
}
