package tech.ankainn.edanapplication.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import tech.ankainn.edanapplication.model.app.master.DataEntity;

@Dao
public interface DataCodesDao {

    @Query("SELECT * FROM master_table WHERE type = :type ORDER BY code")
    List<DataEntity> loadDataCodesByType(int type);

    @Query("SELECT * FROM master_table WHERE owner_code = :ownerCode ORDER BY code")
    List<DataEntity> loadDataCodesByOwner(Integer ownerCode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataCodes(DataEntity... dataEntities);
}
