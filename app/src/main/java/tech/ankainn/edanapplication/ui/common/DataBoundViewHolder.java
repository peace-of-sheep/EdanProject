package tech.ankainn.edanapplication.ui.common;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class DataBoundViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public T binding;

    public DataBoundViewHolder(@NonNull T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
