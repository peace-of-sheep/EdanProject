package tech.ankainn.edanapplication.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import tech.ankainn.edanapplication.model.app.ubigeo.UbigeoLocation;

@Dao
public interface UbigeoDao {

    @Query("SELECT * FROM ubigeo_table WHERE owner_code = :ownerCode")
    List<UbigeoLocation> loadUbigeosByOwnerCode(String ownerCode);

    @Query("SELECT * FROM ubigeo_table WHERE code = :code")
    UbigeoLocation loadUbigeoByCode(String code);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUbigeos(List<UbigeoLocation> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDept(UbigeoLocation... ubigeoLocations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProv(UbigeoLocation... ubigeoLocations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDist(UbigeoLocation... ubigeoLocations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocal(UbigeoLocation... ubigeoLocations);
}
