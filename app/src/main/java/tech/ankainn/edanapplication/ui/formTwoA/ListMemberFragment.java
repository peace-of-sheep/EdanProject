package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import tech.ankainn.edanapplication.databinding.FragmentMembersBinding;
import tech.ankainn.edanapplication.databinding.LayoutItemMemberBinding;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter2;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class ListMemberFragment extends BindingFragment<FragmentMembersBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideViewModelFactory(requireContext());
        MembersViewModel viewModel = new ViewModelProvider(this, factory).get(MembersViewModel.class);

        BindingAdapter2<LayoutItemMemberBinding, MemberData> adapter =
                new BindingAdapter2<LayoutItemMemberBinding, MemberData>(
                        (oldItem, newItem) -> oldItem.tempId == newItem.tempId,
                        (oldItem, newItem) -> Objects.equals(oldItem.dataVersion, newItem.dataVersion),
                        LayoutItemMemberBinding::setMember
                ) {};
        binding().recyclerView.setAdapter(adapter);

        viewModel.getListMemberData().observe(getViewLifecycleOwner(), adapter::submitList);

        binding().btnAddFamily.setOnClickListener(v -> {
            viewModel.createMemberData();
            MemberDialogFragment.showFragment(getParentFragmentManager());
        });


        /*binding().itemHead.cardView.setOnClickListener(v ->
                MemberDialogFragment.showFragment(getParentFragmentManager(), binding().itemHead.getMember().tempId));*/
    }
}
