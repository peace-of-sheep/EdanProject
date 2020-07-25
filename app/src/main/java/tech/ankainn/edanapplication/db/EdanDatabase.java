package tech.ankainn.edanapplication.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tech.ankainn.edanapplication.model.dto.FormTwoEntity;
import tech.ankainn.edanapplication.model.dto.MemberEntity;

@Database(entities = {FormTwoEntity.class, MemberEntity.class}, version = 1, exportSchema = false)
public abstract class EdanDatabase extends RoomDatabase {

    public abstract FormTwoDao formTwoDao();

    private static volatile EdanDatabase instance;

    public static EdanDatabase getInstance(final Context context) {
        if(instance == null) {
            synchronized (EdanDatabase.class) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context, EdanDatabase.class, "edan_database")
                            .build();
                }
            }
        }
        return instance;
    }
}
