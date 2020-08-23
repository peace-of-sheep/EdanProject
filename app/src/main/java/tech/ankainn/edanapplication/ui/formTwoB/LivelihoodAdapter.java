package tech.ankainn.edanapplication.ui.formTwoB;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.databinding.LayoutItemLivelihoodBinding;
import tech.ankainn.edanapplication.model.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter;

public class LivelihoodAdapter extends BindingAdapter<LivelihoodData, LayoutItemLivelihoodBinding> {

    private final OnItemClickListener listener;

    public LivelihoodAdapter(OnItemClickListener listener, AppExecutors appExecutors) {
        super(appExecutors, new DiffUtil.ItemCallback<LivelihoodData>() {
            @Override
            public boolean areItemsTheSame(@NonNull LivelihoodData oldItem, @NonNull LivelihoodData newItem) {
                return oldItem.tempId == newItem.tempId;
            }

            @Override
            public boolean areContentsTheSame(@NonNull LivelihoodData oldItem, @NonNull LivelihoodData newItem) {
                return oldItem.dataVersion == newItem.dataVersion;
            }
        });
        this.listener = listener;
    }

    @Override
    protected LayoutItemLivelihoodBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        LayoutItemLivelihoodBinding binding = LayoutItemLivelihoodBinding.inflate(inflater, parent, false);
        binding.cardView.setOnClickListener(v -> listener.onClick(binding.getLivelihood()));
        return binding;
    }

    @Override
    protected void bind(LayoutItemLivelihoodBinding binding, LivelihoodData livelihoodData) {
        binding.setLivelihood(livelihoodData);
    }

    public interface OnItemClickListener {
        void onClick(LivelihoodData data);
    }
}
