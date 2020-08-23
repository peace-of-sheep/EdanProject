package tech.ankainn.edanapplication.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class EdanTypeConverters {

    private static Gson gson = new Gson();

    @TypeConverter
    public static String boolToString(List<Boolean> source) {
        return gson.toJson(source);
    }

    @TypeConverter
    public static List<Boolean> stringToBool(String source) {
        if (source == null) {
            return Collections.emptyList();
        }
        Type type = new TypeToken<List<Boolean>>() {}.getType();
        return gson.fromJson(source, type);
    }

    @TypeConverter
    public static String intsToString(List<Integer> source) {
        return gson.toJson(source);
    }

    @TypeConverter
    public static List<Integer> stringToInts(String source) {
        if (source == null) {
            return Collections.emptyList();
        }
        Type type = new TypeToken<List<Integer>>() {}.getType();
        return gson.fromJson(source, type);
    }
}
