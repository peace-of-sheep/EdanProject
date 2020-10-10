package tech.ankainn.edanapplication.ui.formOne;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.ankainn.edanapplication.databinding.LayoutItemQuanSwitchBinding;
import tech.ankainn.edanapplication.model.app.formOne.SelectableItemData;
import tech.ankainn.edanapplication.ui.common.BindingViewHolder;

public class SwitchableQuanAdapter extends RecyclerView.Adapter<BindingViewHolder<LayoutItemQuanSwitchBinding>> {

    private static final String DATA = "data";

    private final String[] names;

    private List<SelectableItemData> selectableHolderData;

    public SwitchableQuanAdapter(String[] names) {
        this.names = names;
    }

    @NonNull
    @Override
    public BindingViewHolder<LayoutItemQuanSwitchBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemQuanSwitchBinding binding =
                LayoutItemQuanSwitchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.switchView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.textQuantity.setEnabled(isChecked);
            binding.getHolder().selection = isChecked;
        });
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<LayoutItemQuanSwitchBinding> holder, int position) {
        holder.binding.setTitle(names[position]);
        if (selectableHolderData != null) {
            holder.binding.textQuantity.setEnabled(selectableHolderData.get(position).selection);
            holder.binding.setHolder(selectableHolderData.get(position));
        }
        holder.binding.executePendingBindings();
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<LayoutItemQuanSwitchBinding> holder, int position, @NonNull List<Object> payloads) {
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
        return names.length;
    }

    public void submitList(List<SelectableItemData> holderData) {
        if (holderData.size() != getItemCount())
            throw new RuntimeException("Must have the same size");

        this.selectableHolderData = holderData;
        notifyItemRangeChanged(0, getItemCount(), DATA);
    }
}
