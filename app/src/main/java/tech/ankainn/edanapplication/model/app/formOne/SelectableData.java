package tech.ankainn.edanapplication.model.app.formOne;

import java.util.ArrayList;
import java.util.List;

public class SelectableData {

    public List<SelectableItemData> list = new ArrayList<>();
    public String observation = "";

    /*public SelectableData() {
    }*/

    public SelectableData(int n) {
        for (int i = 0; i < n; i++) {
            list.add(new SelectableItemData());
        }
    }

    /*public SelectableData(List<SelectableItemData> list) {
        this.list = list;
    }*/
}
