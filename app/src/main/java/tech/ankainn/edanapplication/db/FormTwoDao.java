package tech.ankainn.edanapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.model.dto.FormTwoComplete;
import tech.ankainn.edanapplication.model.dto.FormTwoSubset;

@Dao
public abstract class FormTwoDao {

    @Query("SELECT form_two_id, form_two_api_id, data_version, department, province, district, date_event, hour_event, lot FROM form_two_table ORDER BY form_two_id DESC")
    public abstract LiveData<List<FormTwoSubset>> loadAllFormTwoSubset();

    @Transaction
    @Query("SELECT * FROM form_two_table WHERE form_two_id = :formTwoId")
    public abstract FormTwoComplete loadFormTwoById(long formTwoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertFormTwo(FormTwoData formTwoData);

    @Update
    public abstract void updateFormTwo(FormTwoData formTwoData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMember(MemberData memberData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAllMembers(List<MemberData> memberDataList);

    @Update
    public abstract void updateMember(MemberData memberData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertLivelihood(LivelihoodData livelihoodData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAllLivelihoods(List<LivelihoodData> livelihoodDataList);

    @Update
    public abstract void updateLivelihood(LivelihoodData livelihoodData);

    @Transaction
    public void insertFormTwoComplete(FormTwoComplete formTwoComplete) {
        FormTwoData formTwoData = formTwoComplete.formTwoData;
        List<MemberData> memberDataList = formTwoComplete.memberDataList;
        List<LivelihoodData> livelihoodDataList = formTwoComplete.livelihoodDataList;

        final long formTwoId = insertFormTwo(formTwoData);

        for (MemberData memberData : memberDataList) {
            memberData.formTwoOwnerId = formTwoId;
        }
        insertAllMembers(memberDataList);

        for (LivelihoodData livelihoodData : livelihoodDataList) {
            livelihoodData.formTwoOwnerId = formTwoId;
        }
        insertAllLivelihoods(livelihoodDataList);
    }

    @Transaction
    public void updateFormTwoComplete(FormTwoComplete formTwoComplete) {
        FormTwoData formTwoData = formTwoComplete.formTwoData;
        List<MemberData> memberDataList = formTwoComplete.memberDataList;
        List<LivelihoodData> livelihoodDataList = formTwoComplete.livelihoodDataList;

        updateFormTwo(formTwoData);

        for (MemberData memberData : memberDataList) {
            if(memberData.id == 0) {
                memberData.formTwoOwnerId = formTwoData.id;
                insertMember(memberData);
            } else {
                updateMember(memberData);
            }
        }

        for (LivelihoodData livelihoodData : livelihoodDataList) {
            if (livelihoodData.id == 0) {
                livelihoodData.formTwoOwnerId = formTwoData.id;
                insertLivelihood(livelihoodData);
            } else {
                updateLivelihood(livelihoodData);
            }
        }
    }
}
