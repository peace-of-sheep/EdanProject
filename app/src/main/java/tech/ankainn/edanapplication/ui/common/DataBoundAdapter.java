package tech.ankainn.edanapplication.ui.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class DataBoundAdapter<D extends ViewDataBinding> extends RecyclerView.Adapter<DataBoundViewHolder<D>> {
    @NonNull
    @Override
    public DataBoundViewHolder<D> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DataBoundViewHolder<D> holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
