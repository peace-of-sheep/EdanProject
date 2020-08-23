package tech.ankainn.edanapplication.model.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "livelihood_table")
public class LivelihoodEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "livelihood_id")
    public long livelihoodId;

    @ColumnInfo(name = "form_two_owner_id")
    public long formTwoOwnerId;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion;
    public String name;
    public String type;
    @ColumnInfo(name = "amount_lost")
    public Integer amountLost;
    @ColumnInfo(name = "amount_affected")
    public Integer amountAffected;
}
