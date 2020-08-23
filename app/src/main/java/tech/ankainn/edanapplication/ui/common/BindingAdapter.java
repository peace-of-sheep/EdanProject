package tech.ankainn.edanapplication.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.viewbinding.ViewBinding;

import tech.ankainn.edanapplication.AppExecutors;

public abstract class BindingAdapter<D, VB extends ViewBinding> extends ListAdapter<D, BindingViewHolder<VB>> {

    protected BindingAdapter(AppExecutors appExecutors, DiffUtil.ItemCallback<D> diffCallback) {
        super(new AsyncDifferConfig.Builder<>(diffCallback)
                .setBackgroundThreadExecutor(appExecutors.diskIO())
                .build());
    }

    protected abstract VB createBinding(LayoutInflater inflater, ViewGroup parent);

    protected abstract void bind(VB binding, D d);

    @NonNull
    @Override
    public BindingViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VB binding = createBinding(inflater, parent);
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<VB> holder, int position) {
        bind(holder.binding, getItem(position));
        if(holder.binding instanceof ViewDataBinding) {
            ((ViewDataBinding) holder.binding).executePendingBindings();
        }
    }
}
