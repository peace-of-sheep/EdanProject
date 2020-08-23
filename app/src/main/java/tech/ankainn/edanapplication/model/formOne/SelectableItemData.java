package tech.ankainn.edanapplication.model.formOne;

import org.jetbrains.annotations.NotNull;

public class SelectableItemData {

    public Boolean selection;
    public Integer quantity;

    public SelectableItemData(Boolean selection, Integer quantity) {
        this.selection = selection;
        this.quantity = quantity;
    }

    @NotNull
    @Override
    public String toString() {
        return "Holder{" +
                "selection=" + selection +
                ", quantity=" + quantity +
                '}';
    }
}
