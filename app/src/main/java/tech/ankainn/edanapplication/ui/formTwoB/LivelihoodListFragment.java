package tech.ankainn.edanapplication.ui.formTwoB;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import tech.ankainn.edanapplication.databinding.FragmentLivelihoodListBinding;
import tech.ankainn.edanapplication.databinding.LayoutItemLivelihoodBinding;
import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter2;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class LivelihoodListFragment extends BindingFragment<FragmentLivelihoodListBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        LivelihoodViewModel viewModel = new ViewModelProvider(this, factory).get(LivelihoodViewModel.class);

        BindingAdapter2<LayoutItemLivelihoodBinding, LivelihoodData> adapter;
        adapter = new BindingAdapter2<LayoutItemLivelihoodBinding, LivelihoodData>(
                (oldItem, newItem) -> oldItem.tempId == newItem.tempId,
                (oldItem, newItem) -> Objects.equals(oldItem.dataVersion, newItem.dataVersion),
                LayoutItemLivelihoodBinding::setLivelihood
        ) {};

        binding().recyclerView.setAdapter(adapter);

        viewModel.getLivelihoodDataList().observe(getViewLifecycleOwner(), adapter::submitList);
        viewModel.getNameMembers().observe(getViewLifecycleOwner(), names -> binding().setNames(names));

        binding().comboBox.setOnItemClickListener((parent, view, position, id) -> viewModel.onOption(position));

        binding().btnAdd.setOnClickListener(v -> {
            long tempMemberId = viewModel.getCurrentMemberId();
            if (tempMemberId != -1) {
                LivelihoodDialogFragment.showFragment(getParentFragmentManager(), tempMemberId);
            }
        });
    }
}
