package tech.ankainn.edanapplication.model.formTwo;

import org.jetbrains.annotations.NotNull;

public class MemberData {

    public long tempId;

    public long id;

    public long formTwoOwnerId;

    public Integer dataVersion;
    public String name;
    public Integer age;
    public String gender;
    public String identificationType;
    public Integer identificationNumber;
    public String condition;
    public String personalInjury;

    public boolean notEmpty() {
        return name != null && gender != null && identificationType != null && identificationNumber != null && condition != null && personalInjury != null;
    }

    @NotNull
    @Override
    public String toString() {
        return "MemberData{" +
                "id=" + id +
                ", dataVersion=" + dataVersion +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", idType='" + identificationType + '\'' +
                ", idNumber=" + identificationNumber +
                ", condition='" + condition + '\'' +
                ", personalInjury='" + personalInjury + '\'' +
                '}';
    }
}
