package tech.ankainn.edanapplication.model.app.formTwo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

@Entity(foreignKeys = {
            @ForeignKey(entity = MemberData.class,
                        parentColumns = "member_id",
                        childColumns = "member_owner_id",
                        onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = FormTwoData.class,
                        parentColumns = "form_two_id",
                        childColumns = "form_two_owner_id",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {
            @Index(value = "member_owner_id"),
            @Index(value = "form_two_owner_id")
        },
        tableName = "livelihood_table")
public class LivelihoodData {

    @Ignore
    public long tempId;

    @Ignore
    public long tempMemberId;

    @Ignore
    public boolean toRemove = false;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "livelihood_id")
    public long id;

    @ColumnInfo(name = "form_two_owner_id")
    public long formTwoOwnerId;

    @ColumnInfo(name = "member_owner_id")
    public long memberOwnerId;

    @ColumnInfo(name = "data_version")
    public Integer dataVersion = 0;

    public Integer code = -1;
    public String name = "";

    @ColumnInfo(name = "code_type")
    public Integer codeType = -1;
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
