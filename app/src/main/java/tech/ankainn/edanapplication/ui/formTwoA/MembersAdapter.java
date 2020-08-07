package tech.ankainn.edanapplication.ui.formTwoA;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Objects;

import tech.ankainn.edanapplication.databinding.LayoutItemMemberBinding;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter;

public class MembersAdapter extends BindingAdapter<MemberData, LayoutItemMemberBinding> {

    public final OnClick listener;

    public MembersAdapter(OnClick listener) {
        this.listener = listener;
    }

    @Override
    protected LayoutItemMemberBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        LayoutItemMemberBinding binding = LayoutItemMemberBinding.inflate(inflater, parent, false);
        binding.cardView.setOnClickListener(v -> listener.onClick(binding.getMember()));
        return binding;
    }

    @Override
    protected void bind(LayoutItemMemberBinding binding, MemberData householdMemberData) {
        binding.setMember(householdMemberData);
    }

    @Override
    protected boolean areItemsTheSame(MemberData oldItem, MemberData newItem) {
        return oldItem.tempId == newItem.tempId;
    }

    @Override
    protected boolean areContentsTheSame(MemberData oldItem, MemberData newItem) {
        return Objects.equals(oldItem.dataVersion, newItem.dataVersion);
    }

    public interface OnClick {
        void onClick(MemberData memberData);
    }
}
