package tech.ankainn.edanapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import tech.ankainn.edanapplication.model.dto.FormTwoWithMembers;

@Dao
public interface FormTwoDao {

    @Transaction
    @Query("SELECT * FROM form_two_table")
    LiveData<List<FormTwoWithMembers>> getAllFormTwoWithMembers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFormTwo(FormTwoWithMembers formTwoWithMembers);
}
