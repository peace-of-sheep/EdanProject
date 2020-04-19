package tech.ankainn.edanapplication.ui.formOne;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tech.ankainn.edanapplication.databinding.LayoutItemSwitchBinding;
import tech.ankainn.edanapplication.ui.common.DataBoundViewHolder;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length;
    }

    /*public SwitchableAdapter(String[] list, boolean quantityVisibility, boolean switchVisibility) {
            this.list = list;
            this.quantityVisibility = quantityVisibility;
            this.switchVisibility = switchVisibility;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            LayoutItemSwitchBinding binding = LayoutItemSwitchBinding.inflate(inflater, parent, false);
            binding.setQuantityVisibility(quantityVisibility);
            binding.setSwitchVisibility(switchVisibility);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String title = list[position];
            holder.binding.setTitle(title);
        }*/
    static class Holder extends DataBoundViewHolder<LayoutItemSwitchBinding> {
        public Holder(@NonNull LayoutItemSwitchBinding binding) {
            super(binding);
        }
    }
}
