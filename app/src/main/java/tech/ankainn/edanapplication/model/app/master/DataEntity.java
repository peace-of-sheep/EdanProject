package tech.ankainn.edanapplication.model.app.master;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "master_table", indices = @Index(value = "code", unique = true))
public class DataEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public Integer code = -1;
    public String name = "";
    public Integer type = 0;

    @ColumnInfo(name = "owner_code")
    public Integer ownerCode = -1;

    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
