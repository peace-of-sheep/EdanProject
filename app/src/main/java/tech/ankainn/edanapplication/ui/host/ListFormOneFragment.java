package tech.ankainn.edanapplication.ui.host;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.viewbinding.ViewBinding;

import com.google.gson.Gson;

import java.util.Objects;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.LayoutItemFormOneBinding;
import tech.ankainn.edanapplication.databinding.LayoutListBinding;
import tech.ankainn.edanapplication.databinding.LayoutLivelihoodBindingImpl;
import tech.ankainn.edanapplication.model.formOne.FormOneData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter2;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.ui.formOne.FormOneHostFragment;
import tech.ankainn.edanapplication.ui.formOne.FormOneViewModel;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.Tagger;
import tech.ankainn.edanapplication.util.Tuple2;
import tech.ankainn.edanapplication.util.ViewBindingUtil;
import tech.ankainn.edanapplication.viewmodel.FilesViewModelFactory;
import tech.ankainn.edanapplication.viewmodel.FormTwoViewModelFactory;
import timber.log.Timber;

public class ListFormOneFragment extends BindingFragment<LayoutListBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelStoreOwner owner = ScopeNavHostFragment.getOwner(this);
        FilesViewModelFactory factory = InjectorUtil.provideFilesViewModelFactory(requireContext());
        FilesViewModel viewModel = new ViewModelProvider(owner, factory).get(FilesViewModel.class);

        BindingAdapter2<LayoutItemFormOneBinding, Tuple2<Boolean, FormOneData>> adapter =
                new BindingAdapter2<LayoutItemFormOneBinding, Tuple2<Boolean, FormOneData>>(
                        (oldItem, newItem) -> oldItem.second.id == newItem.second.id,
                        (oldItem, newItem) -> Objects.equals(oldItem.second.dataVersion, newItem.second.dataVersion),
                        (binding, data) -> {
                            binding.setFormOne(data.second);
                            binding.setLoading(data.first);
                        }
                ) {}
                .setOnItemCLick(itemBinding -> {
                    Timber.tag(Tagger.DUMPER).d("onActivityCreated: %s", new Gson().toJson(itemBinding.getFormOne()));
                    Navigation.findNavController(requireActivity(), R.id.fragment_container)
                            .navigate(HostFragmentDirections.actionHostToFormOne().setFormOneId(itemBinding.getFormOne().id));
                })
                .setOnLongItemClick(itemBinding ->
                        Toast.makeText(requireContext(), "Long click", Toast.LENGTH_SHORT).show())
                .addBindingPayload("loading", (binding, data) -> binding.setLoading(data.first));
        binding().recyclerView.setAdapter(adapter);

        viewModel.getListFormOne().observe(getViewLifecycleOwner(), adapter::submitList);
    }
}
