package tech.ankainn.edanapplication.ui.common;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public abstract class BindingAdapter<D, VB extends ViewBinding> extends RecyclerView.Adapter<BindingViewHolder<VB>> {

    private List<D> items;

    private int dataVersion = 0;

    protected abstract VB createBinding(LayoutInflater inflater, ViewGroup parent);

    protected abstract void bind(VB binding, D d);

    protected abstract boolean areItemsTheSame(D oldItem, D newItem);

    protected abstract boolean areContentsTheSame(D oldItem, D newItem);

    protected void currentHolder(BindingViewHolder<VB> holder) {

    }

    @NonNull
    @Override
    public BindingViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VB binding = createBinding(inflater, parent);
        BindingViewHolder<VB> holder = new BindingViewHolder<>(binding);
        currentHolder(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<VB> holder, int position) {
        bind(holder.binding, items.get(position));
        if(holder.binding instanceof ViewDataBinding) {
            ((ViewDataBinding) holder.binding).executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @SuppressLint("StaticFieldLeak")
    public void replace(List<D> update) {
        dataVersion ++;
        if (items == null) {
            if (update == null) {
                return;
            }
            items = update;
            notifyDataSetChanged();
        } else if (update == null) {
            int oldSize = items.size();
            items = null;
            notifyItemRangeRemoved(0, oldSize);
        } else {
            final int startVersion = dataVersion;
            final List<D> oldItems = items;
            new AsyncTask<Void, Void, DiffUtil.DiffResult>() {
                @Override
                protected DiffUtil.DiffResult  doInBackground(Void... voids) {
                    return DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return oldItems.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return update.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            D oldItem = oldItems.get(oldItemPosition);
                            D newItem = update.get(newItemPosition);
                            return BindingAdapter.this.areItemsTheSame(oldItem, newItem);
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            D oldItem = oldItems.get(oldItemPosition);
                            D newItem = update.get(newItemPosition);
                            return BindingAdapter.this.areContentsTheSame(oldItem, newItem);
                        }
                    });
                }

                @Override
                protected void onPostExecute(DiffUtil.DiffResult diffResult) {
                    if (startVersion != dataVersion) {
                        // ignore update
                        return;
                    }
                    items = update;
                    diffResult.dispatchUpdatesTo(BindingAdapter.this);

                }
            }.execute();
        }
    }
}
