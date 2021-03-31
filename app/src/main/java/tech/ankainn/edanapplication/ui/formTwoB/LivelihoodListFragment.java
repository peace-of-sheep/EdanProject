package tech.ankainn.edanapplication.ui.formTwoB;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.binding.BindingAdapters;
import tech.ankainn.edanapplication.databinding.FragmentLivelihoodListBinding;
import tech.ankainn.edanapplication.databinding.LayoutItemLivelihoodBinding;
import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter3;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class LivelihoodListFragment extends BindingFragment<FragmentLivelihoodListBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        LivelihoodListViewModel viewModel = new ViewModelProvider(this, factory).get(LivelihoodListViewModel.class);

        BindingAdapter3<LayoutItemLivelihoodBinding, LivelihoodData> adapter = BindingAdapter3.create(
                R.layout.layout_item_livelihood,
                (oldItem, newItem) -> oldItem.tempId == newItem.tempId,
                (oldItem, newItem) -> Objects.equals(oldItem.dataVersion, newItem.dataVersion),
                LayoutItemLivelihoodBinding::setLivelihood
        );
        binding().recyclerView.setAdapter(adapter);

        binding().comboBox.setOnItemClickListener((parent, view, position, id) -> {
            BindingAdapters.showHide(binding().btnAdd, true);
            MemberData current = (MemberData) binding().comboBox.getAdapter().getItem(position);
            viewModel.onMember(current);
        });

        binding().btnAdd.setOnClickListener(v -> {
            long tempMemberId = viewModel.getMemberId();
            if (tempMemberId != 0L) {
                LivelihoodDialogFragment.showFragment(getParentFragmentManager(), tempMemberId);
            }
        });

        viewModel.getMembersList().observe(getViewLifecycleOwner(), members -> binding().setMembers(members));
        viewModel.getLivelihoodDataList().observe(getViewLifecycleOwner(), adapter::submitList);
    }
}
