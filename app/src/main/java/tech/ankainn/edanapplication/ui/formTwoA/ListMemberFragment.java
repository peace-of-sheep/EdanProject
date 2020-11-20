package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import tech.ankainn.edanapplication.FormTwoGraphDirections;
import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentListMemberBinding;
import tech.ankainn.edanapplication.databinding.LayoutItemMemberBinding;
import tech.ankainn.edanapplication.global.AlertDialogFragment;
import tech.ankainn.edanapplication.global.Options;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter2;
import tech.ankainn.edanapplication.ui.common.BindingAdapter3;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.BindingViewHolder;
import tech.ankainn.edanapplication.ui.host.HostFragmentDirections;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class ListMemberFragment extends BindingFragment<FragmentListMemberBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        ListMemberViewModel viewModel = new ViewModelProvider(this, factory).get(ListMemberViewModel.class);

        BindingAdapter3<LayoutItemMemberBinding, MemberData> adapter = BindingAdapter3.create(
                R.layout.layout_item_member,
                (oldItem, newItem) -> oldItem.tempId == newItem.tempId,
                (oldItem, newItem) -> Objects.equals(oldItem.dataVersion, newItem.dataVersion),
                LayoutItemMemberBinding::setMember
        ).setOnHolderCreation(holder -> holder.binding.btnRemove.setOnClickListener(v -> {
            viewModel.setToDeleteMember(holder.binding.getMember());
            new AlertDialogFragment.Builder("delete")
                    .setMessage(R.string.to_delete)
                    .build(getParentFragmentManager());
            /*MemberData memberData = holder.binding.getMember();
            viewModel.onRemoveItem(memberData);*/
        })).setOnItemCLick(memberData -> {
            long tempId = memberData.tempId;
            NavDirections navDirections = FormTwoGraphDirections.actionGlobalMember()
                    .setMemberId(tempId);
            NavHostFragment.findNavController(this).navigate(navDirections);
        });
        binding().recyclerView.setAdapter(adapter);

        viewModel.getListMemberData().observe(getViewLifecycleOwner(), adapter::submitList);

        binding().btnAddFamily.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(FormTwoGraphDirections.actionGlobalMember()));

        Options.getInstance().observe(getViewLifecycleOwner(), (emitter, option) -> {
            if ("delete".equals(emitter)) {
                if (option == 0) {
                    MemberData memberData = viewModel.getToDeleteMember();
                    viewModel.onRemoveItem(memberData);
                }
            }
        });
    }
}
