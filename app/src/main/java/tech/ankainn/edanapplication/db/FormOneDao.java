package tech.ankainn.edanapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tech.ankainn.edanapplication.model.dto.FormOneEntity;

@Dao
public interface FormOneDao {

    @Query("SELECT * FROM form_one_table ORDER BY form_one_id DESC")
    LiveData<List<FormOneEntity>> getAllFormOne();

    @Query("SELECT * FROM form_one_table WHERE form_one_id = :formOneId")
    FormOneEntity loadFormOneById(long formOneId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFormOne(FormOneEntity formOneEntity);

    @Update
    void updateFormOne(FormOneEntity formOneEntity);
}
