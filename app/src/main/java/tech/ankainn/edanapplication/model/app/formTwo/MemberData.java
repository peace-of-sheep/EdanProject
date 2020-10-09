package tech.ankainn.edanapplication.model.app.formTwo;

import org.jetbrains.annotations.NotNull;

public class MemberData {

    public long tempId;

    public long id;

    public long formTwoOwnerId;

    public Integer dataVersion = 0;

    public String surname = "";
    public String name = "";
    public String birthDate = "";
    public Integer age;
    public String gender = "";
    public String codeGender = "";
    public String typeIdentification = "";
    public Integer codeIdentification = -1;
    public Integer textIdentification;
    public String condition = "";
    public String codeCondition = "";
    public String personalInjury = "";
    public String codePersonalInjury = "";

    public boolean notEmpty() {
        return checkValue(surname) && checkValue(name) && checkValue(birthDate);
    }

    private boolean checkValue(String value) {
        return value != null && value.length() > 0;
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
                ", idType='" + typeIdentification + '\'' +
                ", idNumber=" + textIdentification +
                ", condition='" + condition + '\'' +
                ", personalInjury='" + personalInjury + '\'' +
                '}';
    }
}
