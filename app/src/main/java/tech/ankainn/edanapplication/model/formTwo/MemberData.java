package tech.ankainn.edanapplication.model.formTwo;

public class MemberData {

    public long id;

    public Integer dataVersion;
    public String name;
    public Integer age;
    public String gender;
    public String idType;
    public Integer idNumber;
    public String condition;
    public String personalInjury;

    public boolean notEmpty() {
        return name != null && gender != null && idType != null && idNumber != null && condition != null && personalInjury != null;
    }

    @Override
    public String toString() {
        return "MemberData{" +
                "id=" + id +
                ", dataVersion=" + dataVersion +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", idType='" + idType + '\'' +
                ", idNumber=" + idNumber +
                ", condition='" + condition + '\'' +
                ", personalInjury='" + personalInjury + '\'' +
                '}';
    }
}
