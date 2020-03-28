package tech.ankainn.edanapplication.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import tech.ankainn.edanapplication.util.CharSequenceSerializer;

public class AuthCredentials {

    @SerializedName("username")
    @JsonAdapter(CharSequenceSerializer.class)
    private CharSequence charSequence;

    @SerializedName("password")
    @JsonAdapter(CharSequenceSerializer.class)
    private CharSequence charArray;

    public AuthCredentials(CharSequence charSequence, CharSequence charArray) {
        this.charSequence = charSequence;
        this.charArray = charArray;
    }
}
