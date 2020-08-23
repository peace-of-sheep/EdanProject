package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.databinding.FragmentMembersBinding;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class MembersFragment extends BindingFragment<FragmentMembersBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelStoreOwner owner = ScopeNavHostFragment.getOwner(this);
        ViewModelProvider.Factory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        MembersViewModel viewModel = new ViewModelProvider(owner, factory).get(MembersViewModel.class);

        MembersAdapter adapter = new MembersAdapter(memberData ->
                MemberDialogFragment.showFragment(getParentFragmentManager(), memberData.tempId),
                AppExecutors.getInstance());
        binding().recyclerView.setAdapter(adapter);

        viewModel.getListMemberData().observe(getViewLifecycleOwner(), list -> {
            if(list != null && list.size() > 0) {
                List<MemberData> temp = new ArrayList<>(list);
                binding().setHouseholdHead(temp.remove(0));
                adapter.submitList(temp);
            } else {
                binding().setHouseholdHead(viewModel.getTempHead());
            }
        });

        binding().tempHead.btnSave.setOnClickListener(v -> {
            if(binding().getHouseholdHead().notEmpty()) {
                viewModel.pushActiveMemberData();
            } else {
                // todo
                Toast.makeText(requireContext(), "ConstructMore", Toast.LENGTH_SHORT).show();
            }
        });

        binding().btnAddFamily.setOnClickListener(v ->
                MemberDialogFragment.showFragment(getParentFragmentManager()));

        binding().itemHead.cardView.setOnClickListener(v ->
                MemberDialogFragment.showFragment(getParentFragmentManager(), binding().itemHead.getMember().tempId));
    }
}
