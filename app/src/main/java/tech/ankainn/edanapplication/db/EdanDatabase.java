package tech.ankainn.edanapplication.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tech.ankainn.edanapplication.model.app.ubigeo.UbigeoLocation;
import tech.ankainn.edanapplication.model.app.auth.UserData;
import tech.ankainn.edanapplication.model.app.formOne.FormOneData;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.model.app.master.DataEntity;

@Database(entities = {UserData.class, FormOneData.class, FormTwoData.class, MemberData.class, LivelihoodData.class, UbigeoLocation.class, DataEntity.class}, version = 1, exportSchema = false)
public abstract class EdanDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract FormTwoDao formTwoDao();
    public abstract FormOneDao formOneDao();
    public abstract UbigeoDao ubigeoDao();
    public abstract DataCodesDao dataCodesDao();

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
