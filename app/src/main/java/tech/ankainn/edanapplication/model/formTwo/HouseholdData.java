package tech.ankainn.edanapplication.model.formTwo;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class HouseholdData {

    public String address;
    public String lot;
    public Boolean owner;
    public String condition;
    public String roof;
    public Integer idRoof;
    public String wall;
    public Integer idWall;
    public String floor;
    public Integer idFloor;

    @NotNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
