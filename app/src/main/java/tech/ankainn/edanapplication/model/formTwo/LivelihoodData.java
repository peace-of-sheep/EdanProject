package tech.ankainn.edanapplication.model.formTwo;

import org.jetbrains.annotations.NotNull;

public class LivelihoodData {

    public long tempId;

    public long id;

    public long formTwoOwnerId;

    public int dataVersion;

    public String name;
    public String type;
    public Integer amountLost;
    public Integer amountAffected;

    @NotNull
    @Override
    public String toString() {
        return "LivelihoodData{" +
                "id=" + id +
                ", tempId=" + tempId +
                ", dataVersion=" + dataVersion +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", amount_lost=" + amountLost +
                ", amount_affected=" + amountAffected +
                '}';
    }
}
