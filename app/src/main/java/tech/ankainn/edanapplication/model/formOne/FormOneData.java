package tech.ankainn.edanapplication.model.formOne;

import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;

public class FormOneData {

    public long id;

    public Integer dataVersion;
    public GenInfData genInfData;
    public MapLocationData mapLocationData;

    public SelectableData damageOne;
    public SelectableData damageTwo;
    public SelectableData damageThree;

    public SelectableData activities;
    public SelectableData needs;
}
