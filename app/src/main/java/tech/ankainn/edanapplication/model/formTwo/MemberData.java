package tech.ankainn.edanapplication.model.formTwo;

public class MemberData {

    public long id;

    public Boolean head;
    public String name;
    public Integer age;
    public String gender;
    public String idType;
    public Integer idNumber;
    public String condition;
    public String personalInjury;

    public boolean checkData() {
        return head != null && name != null && gender != null && idType != null && idNumber != null && condition != null && personalInjury != null;
    }
}
