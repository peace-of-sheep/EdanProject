package tech.ankainn.edanapplication.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.util.Tuple2;
import tech.ankainn.edanapplication.util.ViewBindingUtil;

public abstract class BindingAdapter2<VB extends ViewBinding, D> extends ListAdapter<D, BindingViewHolder<VB>> {

    private boolean attached;

    final private OnBinding<VB, D> onBinding;

    private OnItemClick<VB> onItemClick;
    private OnLongItemClick<VB> onLongItemClick;

    private final OnHolderCreation<VB> onHolderCreation = holder -> {

    };

    public BindingAdapter2(@NonNull OnItemsTheSame<D> diffItems,
                              @NonNull OnContentTheSame<D> diffContents,
                              @NotNull OnBinding<VB, D> onBinding) {
        super(new AsyncDifferConfig.Builder<>(
                new DiffUtil.ItemCallback<D>() {
                    @Override
                    public boolean areItemsTheSame(@NonNull D oldItem, @NonNull D newItem) {
                        return diffItems.areItemTheSame(oldItem, newItem);
                    }

                    @Override
                    public boolean areContentsTheSame(@NonNull D oldItem, @NonNull D newItem) {
                        return diffContents.areContentsTheSame(oldItem, newItem);
                    }
                })
                .setBackgroundThreadExecutor(AppExecutors.getInstance().diskIO()).build());
        this.onBinding = onBinding;
        attached = false;
    }

    public BindingAdapter2<VB, D> setOnItemCLick(OnItemClick<VB> onItemCLick) {
        if (attached) {
            throw new RuntimeException("Don't set listener after setting adapter");
        }
        this.onItemClick = onItemCLick;
        return this;
    }

    public BindingAdapter2<VB, D> setOnLongItemClick(OnLongItemClick<VB> onLongItemClick) {
        if (attached) {
            throw new RuntimeException("Don't set listener after setting adapter");
        }
        this.onLongItemClick = onLongItemClick;
        return this;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        attached = true;
    }

    @NonNull
    @Override
    public BindingViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VB binding = ViewBindingUtil.inflate(getClass(), inflater, parent, false);
        BindingViewHolder<VB> holder = new BindingViewHolder<>(binding);
        holder.binding.getRoot().setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (onItemClick != null && pos != RecyclerView.NO_POSITION)
                onItemClick.onClick(pos, holder.binding);
        });
        holder.binding.getRoot().setOnLongClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (onLongItemClick != null && pos != RecyclerView.NO_POSITION) {
                onLongItemClick.onLongClick(pos, holder.binding);
                return true;
            } else {
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<VB> holder, int position) {
        onBinding.bind(holder.binding, getItem(position));
        if(holder.binding instanceof ViewDataBinding) {
            ((ViewDataBinding) holder.binding).executePendingBindings();
        }
    }

    public interface OnItemsTheSame<D> {
        boolean areItemTheSame(D oldItem, D newItem);
    }

    public interface OnContentTheSame<D> {
        boolean areContentsTheSame(D oldItem, D newItem);
    }

    public interface OnItemClick<VB extends ViewBinding> {
        void onClick(int pos, VB itemBinding);
    }

    public interface OnLongItemClick<VB extends ViewBinding> {
        void onLongClick(int pos, VB itemBinding);
    }

    public interface OnBinding<VB extends ViewBinding, T> {
        void bind(VB binding, T data);
    }

    public interface OnItemClick2<I> {
        void onClick(I item);
    }

    public interface OnItemLongClick2<I> {
        void onLongClick(I item);
    }

    public interface OnHolderCreation<VB extends ViewBinding> {
        void onHolderCreation(BindingViewHolder<VB> holder);
    }
}
