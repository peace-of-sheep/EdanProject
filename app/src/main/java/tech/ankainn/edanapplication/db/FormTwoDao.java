package tech.ankainn.edanapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import tech.ankainn.edanapplication.model.dto.FormTwoCompleteData;
import tech.ankainn.edanapplication.model.dto.FormTwoEntity;
import tech.ankainn.edanapplication.model.dto.LivelihoodEntity;
import tech.ankainn.edanapplication.model.dto.MemberEntity;
import tech.ankainn.edanapplication.model.formTwo.FormTwoSubset;

@Dao
public abstract class FormTwoDao {

    @Query("SELECT form_two_id, data_version, department, province, district, date, hour, address, lot FROM form_two_table ORDER BY form_two_id DESC")
    public abstract LiveData<List<FormTwoSubset>> loadAllFormTwoSubset();

    @Transaction
    @Query("SELECT * FROM form_two_table ORDER BY form_two_id DESC")
    public abstract LiveData<List<FormTwoCompleteData>> getAllFormTwo();

    @Transaction
    @Query("SELECT * FROM form_two_table WHERE form_two_id = :formTwoId")
    public abstract FormTwoCompleteData loadFormTwoById(long formTwoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertFormTwo(FormTwoEntity formTwoEntity);

    @Update
    public abstract void updateFormTwo(FormTwoEntity formTwoEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMember(MemberEntity memberEntity);

    @Update
    public abstract void updateMember(MemberEntity memberEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertLivelihood(LivelihoodEntity livelihoodEntity);

    @Update
    public abstract void updateLivelihood(LivelihoodEntity livelihoodEntity);

    @Transaction
    public void insertFormTwoComplete(FormTwoEntity formTwoEntity,
                              List<MemberEntity> memberEntityList,
                              List<LivelihoodEntity> livelihoodEntityList) {
        final long formTwoId = insertFormTwo(formTwoEntity);

        if (memberEntityList != null && memberEntityList.size() > 0) {
            for (MemberEntity memberEntity : memberEntityList) {
                memberEntity.formTwoOwnerId = formTwoId;
                insertMember(memberEntity);
            }
        }

        if (livelihoodEntityList != null && livelihoodEntityList.size() > 0) {
            for (LivelihoodEntity livelihoodEntity : livelihoodEntityList) {
                livelihoodEntity.formTwoOwnerId = formTwoId;
                insertLivelihood(livelihoodEntity);
            }
        }
    }

    @Transaction
    public void updateFormTwoComplete(FormTwoEntity formTwoEntity,
                                      List<MemberEntity> memberEntityList,
                                      List<LivelihoodEntity> livelihoodEntityList) {
        updateFormTwo(formTwoEntity);

        for (MemberEntity memberEntity : memberEntityList) {
            if(memberEntity.memberId == 0) {
                memberEntity.formTwoOwnerId = formTwoEntity.formTwoId;
                insertMember(memberEntity);
            } else {
                updateMember(memberEntity);
            }
        }

        for (LivelihoodEntity livelihoodEntity : livelihoodEntityList) {
            if(livelihoodEntity.livelihoodId == 0) {
                livelihoodEntity.formTwoOwnerId = formTwoEntity.formTwoId;
                insertLivelihood(livelihoodEntity);
            } else {
                updateLivelihood(livelihoodEntity);
            }
        }
    }
}
