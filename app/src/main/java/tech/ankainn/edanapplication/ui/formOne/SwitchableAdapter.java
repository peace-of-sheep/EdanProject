package tech.ankainn.edanapplication.ui.formOne;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.ankainn.edanapplication.databinding.LayoutItemSwitchBinding;
import tech.ankainn.edanapplication.model.formOne.SelectableItemData;
import tech.ankainn.edanapplication.ui.common.BindingViewHolder;

public class SwitchableAdapter extends RecyclerView.Adapter<BindingViewHolder<LayoutItemSwitchBinding>> {

    private static final String DATA = "data";

    private final List<String> names;

    private List<SelectableItemData> selectableHolderData;

    public SwitchableAdapter(List<String> names) {
        this.names = names;
    }

    @NonNull
    @Override
    public BindingViewHolder<LayoutItemSwitchBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemSwitchBinding binding =
                LayoutItemSwitchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<LayoutItemSwitchBinding> holder, int position) {
        holder.binding.setTitle(names.get(position));
        if (selectableHolderData != null) {
            holder.binding.setHolder(selectableHolderData.get(position));
        }
        holder.binding.executePendingBindings();
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<LayoutItemSwitchBinding> holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            for (Object payload : payloads) {
                if (payload.equals(DATA)) {
                    holder.binding.setHolder(selectableHolderData.get(position));
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public void submitList(List<SelectableItemData> holderData) {
        if (holderData.size() != getItemCount())
            throw new RuntimeException("Must have the same size");

        this.selectableHolderData = holderData;
        notifyItemRangeChanged(0, getItemCount(), DATA);
    }
}
