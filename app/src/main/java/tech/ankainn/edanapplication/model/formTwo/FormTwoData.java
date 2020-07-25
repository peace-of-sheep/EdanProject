package tech.ankainn.edanapplication.model.formTwo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FormTwoData {

    public int id;

    public int dataVersion;

    public Integer formTwoApiId;

    public GenInfData genInfData;
    public MapLocationData mapLocationData;
    public HouseholdData householdData;
    public List<MemberData> listMember;

    @NotNull
    @Override
    public String toString() {
        return "FormTwoData{" +
                "id=" + id +
                ", dataVersion=" + dataVersion +
                ", formTwoApiId=" + formTwoApiId +
                ", genInfData=" + genInfData +
                /*", mapLocationData=" + mapLocationData +
                ", householdData=" + householdData +*/
                '}';
    }
}
