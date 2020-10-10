package tech.ankainn.edanapplication.model.app.formOne;

import org.jetbrains.annotations.NotNull;

public class SelectableItemData {

    public Boolean selection = false;
    public Integer quantity;

    @NotNull
    @Override
    public String toString() {
        return "Holder{" +
                "selection=" + selection +
                ", quantity=" + quantity +
                '}';
    }
}
