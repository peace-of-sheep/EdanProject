package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import tech.ankainn.edanapplication.FormTwoGraphDirections;
import tech.ankainn.edanapplication.databinding.FragmentListMemberBinding;
import tech.ankainn.edanapplication.databinding.LayoutItemMemberBinding;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter2;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class ListMemberFragment extends BindingFragment<FragmentListMemberBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        MemberViewModel viewModel = new ViewModelProvider(this, factory).get(MemberViewModel.class);

        BindingAdapter2<LayoutItemMemberBinding, MemberData> adapter =
                new BindingAdapter2<LayoutItemMemberBinding, MemberData>(
                        (oldItem, newItem) -> oldItem.tempId == newItem.tempId,
                        (oldItem, newItem) -> Objects.equals(oldItem.dataVersion, newItem.dataVersion),
                        LayoutItemMemberBinding::setMember
                ) {}
                .setOnItemCLick((pos, itemBinding) -> {
                    long tempId = itemBinding.getMember().tempId;
                    NavDirections navDirections = FormTwoGraphDirections.actionGlobalMember()
                            .setMemberId(tempId);
                    NavHostFragment.findNavController(this).navigate(navDirections);
                });
        binding().recyclerView.setAdapter(adapter);

        viewModel.getListMemberData().observe(getViewLifecycleOwner(), adapter::submitList);

        binding().btnAddFamily.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(FormTwoGraphDirections.actionGlobalMember()));
    }
}
