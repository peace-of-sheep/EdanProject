package tech.ankainn.edanapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import tech.ankainn.edanapplication.model.dto.FormTwoEntity;
import tech.ankainn.edanapplication.model.dto.FormTwoWithMembers;
import tech.ankainn.edanapplication.model.dto.MemberEntity;

@Dao
public abstract class FormTwoDao {

    @Transaction
    @Query("SELECT * FROM form_two_table")
    public abstract LiveData<List<FormTwoWithMembers>> getAllFormTwoWithMembers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertFormTwo(FormTwoEntity formTwoEntity);

    @Update
    public abstract void updateFormTwo(FormTwoEntity formTwoEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMember(MemberEntity memberEntity);

    @Update
    public abstract void updateMember(MemberEntity memberEntity);

    @Transaction
    public void insertFormTwoWithMember(FormTwoEntity formTwoEntity, List<MemberEntity> memberEntityList) {
        final long formTwoId = insertFormTwo(formTwoEntity);

        for (MemberEntity memberEntity : memberEntityList) {
            memberEntity.formTwoOwnerId = formTwoId;
            insertMember(memberEntity);
        }
    }

    @Transaction
    public void updateFormTwoWithMember(FormTwoEntity formTwoEntity, List<MemberEntity> memberEntityList) {
        updateFormTwo(formTwoEntity);

        for (MemberEntity memberEntity : memberEntityList) {
            if(memberEntity.memberId == 0) {
                memberEntity.formTwoOwnerId = formTwoEntity.formTwoId;
                insertMember(memberEntity);
            } else {
                updateMember(memberEntity);
            }
        }
    }
}
