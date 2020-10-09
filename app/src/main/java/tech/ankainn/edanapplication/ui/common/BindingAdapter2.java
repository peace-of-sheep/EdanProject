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

public class BindingAdapter2<VB extends ViewBinding, D> extends ListAdapter<D, BindingViewHolder<VB>> {

    private boolean attached;

    final private OnBinding<VB, D> onBinding;

    private OnItemClick<VB> onItemClick;
    private OnLongItemClick<VB> onLongItemClick;

    private List<Tuple2<String, OnBindingPayload<VB, D>>> listPayloads;

    protected BindingAdapter2(@NonNull OnItemsTheSame<D> diffItems,
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
        listPayloads = new ArrayList<>();
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

    public BindingAdapter2<VB, D> addBindingPayload(String payload, OnBindingPayload<VB, D> onBindingPayload) {
        if (attached) {
            throw new RuntimeException("Don't set listener after setting adapter");
        }
        listPayloads.add(new Tuple2<>(payload, onBindingPayload));
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

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<VB> holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            D item = getItem(position);
            for (Object payload : payloads) {
                for (Tuple2<String, OnBindingPayload<VB, D>> listPayload : listPayloads) {
                    if (payload.equals(listPayload.first)) {
                        listPayload.second.bindPayload(holder.binding, item);
                    }
                }
            }
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

    public interface OnBindingPayload<VB extends ViewBinding, T> {
        void bindPayload(VB binding, T data);
    }
}
