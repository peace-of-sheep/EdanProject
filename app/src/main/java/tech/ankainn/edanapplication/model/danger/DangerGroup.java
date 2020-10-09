package tech.ankainn.edanapplication.model.danger;

import java.util.ArrayList;
import java.util.List;

public class DangerGroup {

    public final String idNumber;
    public final String name;
    public final List<DangerType> listDangers;

    private DangerGroup(String idNumber, String name, List<DangerType> listDangers) {
        this.idNumber = idNumber;
        this.name = name;
        this.listDangers = listDangers;
    }

    public static class Builder {

        private final String idNumber;
        private final String name;
        private List<DangerType> list = new ArrayList<>();

        public Builder(String idNumber, String name) {
            this.idNumber = idNumber;
            this.name = name;
        }

        public Builder addDanger(String idNumber, String name) {
            list.add(new DangerType(name, idNumber));
            return this;
        }

        public DangerGroup build() {
            return new DangerGroup(idNumber, name, list);
        }
    }
}
