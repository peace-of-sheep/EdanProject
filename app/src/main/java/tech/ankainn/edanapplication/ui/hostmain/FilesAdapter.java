package tech.ankainn.edanapplication.ui.hostmain;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.ankainn.edanapplication.databinding.LayoutItemFormTwoBinding;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter;
import tech.ankainn.edanapplication.ui.common.BindingViewHolder;
import tech.ankainn.edanapplication.util.Metadata;
import timber.log.Timber;

public class FilesAdapter extends BindingAdapter<Metadata<FormTwoData>, LayoutItemFormTwoBinding> {

    private final FileClickListener fileClickListener;

    public FilesAdapter(FileClickListener fileClickListener) {
        this.fileClickListener = fileClickListener;
    }

    @Override
    protected void currentHolder(BindingViewHolder<LayoutItemFormTwoBinding> holder) {
        holder.binding.cardView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {

                FormTwoData formTwoData = holder.binding.getFormTwo();
                boolean loading = holder.binding.getLoading();
                fileClickListener.onClick(formTwoData, loading);
            }
        });
    }

    @Override
    protected LayoutItemFormTwoBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return LayoutItemFormTwoBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(LayoutItemFormTwoBinding binding, Metadata<FormTwoData> metadata) {
        binding.setFormTwo(metadata.data);
        binding.setLoading(metadata.loading);
    }

    @Override
    protected boolean areItemsTheSame(Metadata<FormTwoData> oldItem, Metadata<FormTwoData> newItem) {
        Timber.d("areItemsTheSame: %s %s", oldItem.data.id, newItem.data.id);
        return oldItem.data.id == newItem.data.id;
    }

    @Override
    protected boolean areContentsTheSame(Metadata<FormTwoData> oldItem, Metadata<FormTwoData> newItem) {
        Timber.w("areContentsTheSame: %s %s", oldItem.data.dataVersion, newItem.data.dataVersion);
        Timber.w("areContentsTheSame: %s %s", oldItem.loading, newItem.loading);
        return oldItem.data.dataVersion == newItem.data.dataVersion && oldItem.loading == newItem.loading;
    }

    public interface FileClickListener {
        void onClick(FormTwoData binding, boolean loading);
    }
}
