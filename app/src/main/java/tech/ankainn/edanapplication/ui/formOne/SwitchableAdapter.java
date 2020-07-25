package tech.ankainn.edanapplication.ui.formOne;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tech.ankainn.edanapplication.databinding.LayoutItemSwitchBinding;
import tech.ankainn.edanapplication.ui.common.BindingViewHolder;

public class SwitchableAdapter extends RecyclerView.Adapter<SwitchableAdapter.Holder> {

    private final String[] data;

    private final boolean quantityVisibility;
    private final boolean switchVisibility;

    public SwitchableAdapter(String[] list, boolean quantityVisibility, boolean switchVisibility) {
        this.data = list;
        this.quantityVisibility = quantityVisibility;
        this.switchVisibility = switchVisibility;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutItemSwitchBinding binding = LayoutItemSwitchBinding.inflate(inflater, parent, false);
        binding.setQuantityVisibility(quantityVisibility);
        binding.setSwitchVisibility(switchVisibility);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.binding.setTitle(data[position]);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length;
    }

    static class Holder extends BindingViewHolder<LayoutItemSwitchBinding> {

        public Holder(@NonNull LayoutItemSwitchBinding binding) {
            super(binding);
        }
    }
}
