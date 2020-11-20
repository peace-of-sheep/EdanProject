package tech.ankainn.edanapplication.ui.host;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.util.Objects;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.LayoutItemFormOneBinding;
import tech.ankainn.edanapplication.databinding.LayoutListBinding;
import tech.ankainn.edanapplication.model.dto.FormOneSubset;
import tech.ankainn.edanapplication.ui.common.BindingAdapter2;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.Tuple2;

public class ListFormOneFragment extends BindingFragment<LayoutListBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideViewModelFactory(requireContext());
        FilesViewModel viewModel = new ViewModelProvider(this, factory).get(FilesViewModel.class);

        BindingAdapter2<LayoutItemFormOneBinding, FormOneSubset> adapter =
                new BindingAdapter2<LayoutItemFormOneBinding, FormOneSubset>(
                        (oldItem, newItem) -> oldItem.id == newItem.id,
                        (oldItem, newItem) -> Objects.equals(oldItem.dataVersion, newItem.dataVersion) &&
                                oldItem.loading == newItem.loading,
                        (binding, data) -> {
                            binding.setFormOne(data);
                            binding.setLoading(data.loading);
                        }
                ) {}
                .setOnItemCLick((pos, itemBinding) -> {
                    long userId = viewModel.getUserId();
                    String username = viewModel.getUsername();
                    NavDirections directions = HostFragmentDirections.actionHostToFormOne(userId, username)
                            .setFormOneId(itemBinding.getFormOne().id);
                    Navigation.findNavController(requireActivity(), R.id.fragment_container)
                            .navigate(directions);
                });
        binding().recyclerView.setAdapter(adapter);

        binding().setEmptyVisible(true);

        viewModel.getListFormOne().observe(getViewLifecycleOwner(), list -> {
            binding().setEmptyVisible(list == null || list.isEmpty());
            adapter.submitList(list);
        });
    }
}
