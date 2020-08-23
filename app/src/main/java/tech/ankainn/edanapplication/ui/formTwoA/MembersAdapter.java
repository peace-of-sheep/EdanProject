package tech.ankainn.edanapplication.ui.formTwoA;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.databinding.LayoutItemMemberBinding;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter;

public class MembersAdapter extends BindingAdapter<MemberData, LayoutItemMemberBinding> {

    public final OnClick listener;

    protected MembersAdapter(OnClick onClick, AppExecutors appExecutors) {
        super(appExecutors, new DiffUtil.ItemCallback<MemberData>() {
            @Override
            public boolean areItemsTheSame(@NonNull MemberData oldItem, @NonNull MemberData newItem) {
                return oldItem.tempId == newItem.tempId;
            }

            @Override
            public boolean areContentsTheSame(@NonNull MemberData oldItem, @NonNull MemberData newItem) {
                return Objects.equals(oldItem.dataVersion, newItem.dataVersion);
            }
        });
        this.listener = onClick;
    }

    @Override
    protected LayoutItemMemberBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        LayoutItemMemberBinding binding = LayoutItemMemberBinding.inflate(inflater, parent, false);
        binding.cardView.setOnClickListener(v -> listener.onClick(binding.getMember()));
        return binding;
    }

    @Override
    protected void bind(LayoutItemMemberBinding binding, MemberData memberData) {
        binding.setMember(memberData);
    }

    public interface OnClick {
        void onClick(MemberData memberData);
    }
}
