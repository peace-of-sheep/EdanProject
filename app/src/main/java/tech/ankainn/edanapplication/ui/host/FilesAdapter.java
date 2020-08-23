package tech.ankainn.edanapplication.ui.host;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.viewbinding.ViewBinding;

import java.util.Objects;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.databinding.LayoutItemFormTwoBinding;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter;
import tech.ankainn.edanapplication.util.Tuple2;

public class FilesAdapter extends BindingAdapter<Tuple2<Boolean, FormTwoData>, LayoutItemFormTwoBinding> {

    private final FileClickListener fileClickListener;

    protected FilesAdapter(FileClickListener fileClickListener, AppExecutors appExecutors) {
        super(appExecutors, new DiffUtil.ItemCallback<Tuple2<Boolean, FormTwoData>>() {
            @Override
            public boolean areItemsTheSame(@NonNull Tuple2<Boolean, FormTwoData> oldItem, @NonNull Tuple2<Boolean, FormTwoData> newItem) {
                return oldItem.second.id == newItem.second.id;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Tuple2<Boolean, FormTwoData> oldItem, @NonNull Tuple2<Boolean, FormTwoData> newItem) {
                return Objects.equals(oldItem.second.dataVersion, newItem.second.dataVersion) && Objects.equals(oldItem.first, newItem.first);
            }
        });
        this.fileClickListener = fileClickListener;
    }

    @Override
    protected LayoutItemFormTwoBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        LayoutItemFormTwoBinding binding = LayoutItemFormTwoBinding.inflate(inflater, parent, false);
        binding.cardView.setOnClickListener(v -> {
            FormTwoData formTwoData = binding.getFormTwo();
            boolean loading = binding.getLoading();
            fileClickListener.onClick(formTwoData, loading);
        });
        return binding;
    }

    @Override
    protected void bind(LayoutItemFormTwoBinding binding, Tuple2<Boolean, FormTwoData> tuple2) {
        binding.setFormTwo(tuple2.second);
        binding.setLoading(tuple2.first);
    }

    public interface FileClickListener {
        void onClick(FormTwoData formTwoData, boolean loading);
    }
}
