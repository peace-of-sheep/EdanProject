package tech.ankainn.edanapplication.ui.formTwo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Objects;

import tech.ankainn.edanapplication.databinding.LayoutItemMemberBinding;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter;

public class MembersAdapter extends BindingAdapter<MemberData, LayoutItemMemberBinding> {

    @Override
    protected LayoutItemMemberBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        LayoutItemMemberBinding binding = LayoutItemMemberBinding.inflate(inflater, parent, false);
        binding.cardView.setOnClickListener(v -> {});
        return binding;
    }

    @Override
    protected void bind(LayoutItemMemberBinding binding, MemberData householdMemberData) {
        binding.setMember(householdMemberData);
    }

    @Override
    protected boolean areItemsTheSame(MemberData oldItem, MemberData newItem) {
        return Objects.equals(oldItem.idNumber, newItem.idNumber);
    }

    @Override
    protected boolean areContentsTheSame(MemberData oldItem, MemberData newItem) {
        return Objects.equals(oldItem.idType, newItem.idType) &&
                Objects.equals(oldItem.name, newItem.name) &&
                Objects.equals(oldItem.condition, newItem.condition) &&
                Objects.equals(oldItem.gender, newItem.gender) &&
                Objects.equals(oldItem.age, newItem.age) &&
                Objects.equals(oldItem.personalInjury, newItem.personalInjury);
    }
}
