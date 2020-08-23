package tech.ankainn.edanapplication.model.formOne;

import java.util.List;

public class SelectableData {
    public List<SelectableItemData> list;
    public String observation;

    public SelectableData(List<SelectableItemData> list, String observation) {
        this.list = list;
        this.observation = observation;
    }
}
