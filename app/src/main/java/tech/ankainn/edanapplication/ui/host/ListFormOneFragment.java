package tech.ankainn.edanapplication.ui.host;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;

import java.util.Objects;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.LayoutItemFormOneBinding;
import tech.ankainn.edanapplication.databinding.LayoutListBinding;
import tech.ankainn.edanapplication.model.formOne.FormOneData;
import tech.ankainn.edanapplication.model.formOne.FormOneSubset;
import tech.ankainn.edanapplication.ui.common.BindingAdapter2;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.Tuple2;
import tech.ankainn.edanapplication.viewmodel.FilesViewModelFactory;

public class ListFormOneFragment extends BindingFragment<LayoutListBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelStoreOwner owner = ScopeNavHostFragment.getOwner(this);
        FilesViewModelFactory factory = InjectorUtil.provideFilesViewModelFactory(requireContext());
        FilesViewModel viewModel = new ViewModelProvider(owner, factory).get(FilesViewModel.class);

        BindingAdapter2<LayoutItemFormOneBinding, Tuple2<Boolean, FormOneSubset>> adapter =
                new BindingAdapter2<LayoutItemFormOneBinding, Tuple2<Boolean, FormOneSubset>>(
                        (oldItem, newItem) -> oldItem.second.id == newItem.second.id,
                        (oldItem, newItem) -> Objects.equals(oldItem.second.dataVersion, newItem.second.dataVersion),
                        (binding, data) -> {
                            binding.setFormOne(data.second);
                            binding.setLoading(data.first);
                        }
                ) {}
                .setOnItemCLick(itemBinding -> Navigation.findNavController(requireActivity(), R.id.fragment_container)
                        .navigate(HostFragmentDirections.actionHostToFormOne().setFormOneId(itemBinding.getFormOne().id)))
                .setOnLongItemClick(itemBinding ->
                        Toast.makeText(requireContext(), "Long click", Toast.LENGTH_SHORT).show())
                .addBindingPayload("loading", (binding, data) -> binding.setLoading(data.first));
        binding().recyclerView.setAdapter(adapter);

        viewModel.getListFormOne().observe(getViewLifecycleOwner(), adapter::submitList);
    }
}
