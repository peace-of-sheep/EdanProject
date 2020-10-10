package tech.ankainn.edanapplication.model.app.geninf;

import androidx.room.Embedded;

public class GenInfData {

    @Embedded
    public HeaderData headerData = new HeaderData();

    @Embedded
    public ExtraData extraData = new ExtraData();

    @Embedded
    public MapLocationData mapLocationData = new MapLocationData();
}
