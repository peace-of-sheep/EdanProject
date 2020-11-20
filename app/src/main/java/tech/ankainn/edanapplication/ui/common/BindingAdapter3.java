package tech.ankainn.edanapplication.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.viewbinding.ViewBinding;

import org.jetbrains.annotations.NotNull;

import tech.ankainn.edanapplication.AppExecutors;

public class BindingAdapter3<VB extends ViewDataBinding, D> extends ListAdapter<D, BindingViewHolder<VB>> {

    @LayoutRes
    private final int layoutRes;

    private final OnBinding<VB, D> onBinding;

    private OnItemClick<D> onItemClick;
    private OnItemLongClick<D> onItemLongClick;
    private OnHolderCreation<VB> onHolderCreation;

    private final OnItemClick<D> onItemClickWrapper = item -> {
        if (onItemClick != null) {
            onItemClick.onClick(item);
        }
    };
    private final OnItemLongClick<D> onItemLongClickWrapper = item -> {
        if (onItemLongClick != null) {
            return onItemLongClick.onLongClick(item);
        }
        return false;
    };
    private final OnHolderCreation<VB> onHolderCreationWrapper = holder -> {
        if (onHolderCreation != null) {
            onHolderCreation.onCreation(holder);
        }
    };

    public static <VB extends ViewDataBinding, D> BindingAdapter3<VB, D> create(
            @LayoutRes int layoutRes,
            @NonNull OnItemsTheSame<D> diffItems,
            @NonNull OnContentTheSame<D> diffContents,
            @NotNull OnBinding<VB, D> onBinding
    ) {
        return new BindingAdapter3<>(layoutRes, diffItems, diffContents, onBinding);
    }

    private BindingAdapter3(@LayoutRes int layoutRes,
                            @NonNull OnItemsTheSame<D> diffItems,
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
        this.layoutRes = layoutRes;
        this.onBinding = onBinding;
    }

    public BindingAdapter3<VB, D> setOnItemCLick(OnItemClick<D> onItemCLick) {
            this.onItemClick = onItemCLick;
            return this;
    }

    public BindingAdapter3<VB, D> setOnItemLongClick(OnItemLongClick<D> onItemLongClick) {
            this.onItemLongClick = onItemLongClick;
            return this;
    }

    public BindingAdapter3<VB, D> setOnHolderCreation(OnHolderCreation<VB> onHolderCreation) {
        this.onHolderCreation = onHolderCreation;
        return this;
    }

    @NonNull
    @Override
    public BindingViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        VB binding = DataBindingUtil.inflate(inflater, layoutRes, parent, false);

        BindingViewHolder<VB> holder = new BindingViewHolder<>(binding);

        holder.binding.getRoot().setOnClickListener(v ->
                onItemClickWrapper.onClick(getItem(holder.getAdapterPosition())));

        holder.binding.getRoot().setOnLongClickListener(v ->
                onItemLongClickWrapper.onLongClick(getItem(holder.getAdapterPosition())));

        onHolderCreationWrapper.onCreation(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<VB> holder, int position) {
        onBinding.bind(holder.binding, getItem(position));
        holder.binding.executePendingBindings();
    }

    public interface OnItemsTheSame<D> {
        boolean areItemTheSame(D oldItem, D newItem);
    }

    public interface OnContentTheSame<D> {
        boolean areContentsTheSame(D oldItem, D newItem);
    }

    public interface OnBinding<VB extends ViewBinding, T> {
        void bind(VB binding, T data);
    }

    public interface OnItemClick<I> {
        void onClick(I item);
    }

    public interface OnItemLongClick<I> {
        boolean onLongClick(I item);
    }

    public interface OnHolderCreation<VB extends ViewBinding> {
        void onCreation(BindingViewHolder<VB> holder);
    }
}
