package tech.ankainn.edanapplication.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import tech.ankainn.edanapplication.model.app.auth.UserData;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user_table WHERE hash=:hashcode")
    UserData loadUserDataByHash(String hashcode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(UserData userData);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(UserData userData);
}
