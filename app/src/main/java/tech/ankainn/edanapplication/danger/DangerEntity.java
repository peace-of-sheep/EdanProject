package tech.ankainn.edanapplication.danger;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class DangerEntity {

    public final String ownerCode;
    public final String name;
    public final String code;

    public DangerEntity(String ownerCode, String name, String code) {
        this.ownerCode = ownerCode;
        this.name = name;
        this.code = code;
    }

    public static DangerEntity createWithOwner(String code, String name) {
        String owner = String.format("%c000", code.charAt(0));
        return new DangerEntity(owner, name, code);
    }

    @Override
    @NotNull
    public String toString() {
        return name;
    }

    public String debug() {
        return new Gson().toJson(this);
    }
}
