package tech.ankainn.edanapplication.model.app.formTwo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.app.geninf.GenInfData;

public class FormTwoData {

    public long id;

    public Integer dataVersion = 0;

    public Integer formTwoApiId = -1;

    public GenInfData genInfData = new GenInfData();

    public HouseholdData householdData = new HouseholdData();

    public List<MemberData> listMemberData = new ArrayList<>();

    public List<LivelihoodData> listLivelihood = new ArrayList<>();

    @NotNull
    @Override
    public String toString() {
        return "FormTwoData{" +
                "id=" + id +
                ", dataVersion=" + dataVersion +
                ", formTwoApiId=" + formTwoApiId +
                ", genInfData=" + genInfData +
                ", householdData=" + householdData +
                ", listMemberData=" + listMemberData +
                ", listLivelihood=" + listLivelihood +
                '}';
    }
}
