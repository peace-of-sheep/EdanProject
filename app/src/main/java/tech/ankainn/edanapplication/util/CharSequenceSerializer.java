package tech.ankainn.edanapplication.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class CharSequenceSerializer implements JsonSerializer<CharSequence> {

    @Override
    public JsonElement serialize(CharSequence src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
