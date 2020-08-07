package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentMembersBinding;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.ui.common.BindingFragment;

import static tech.ankainn.edanapplication.util.NavigationUtil.getViewModelProvider;

public class MembersFragment extends BindingFragment<FragmentMembersBinding>{

    @Override
    protected FragmentMembersBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMembersBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavBackStackEntry owner = Navigation
                .findNavController(requireActivity(), R.id.fragment_container)
                .getBackStackEntry(R.id.form_two_host_fragment);
        FormTwoViewModel viewModel = new ViewModelProvider(owner).get(FormTwoViewModel.class);

        MembersAdapter adapter = new MembersAdapter(memberData -> {
            viewModel.updateMember(memberData);
            MemberDialogFragment.showFragment(getParentFragmentManager());
        });
        binding().recyclerView.setAdapter(adapter);

        viewModel.getListMemberData().observe(getViewLifecycleOwner(), list -> {
            if(list != null && list.size() > 0) {
                List<MemberData> temp = new ArrayList<>(list);
                binding().setHouseholdHead(temp.remove(0));
                adapter.replace(temp);
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

        binding().btnAddFamily.setOnClickListener(v -> {
            viewModel.createMember();
            MemberDialogFragment.showFragment(getParentFragmentManager());
        });

        binding().itemHead.cardView.setOnClickListener(v -> {
            viewModel.updateMember(binding().itemHead.getMember());
            MemberDialogFragment.showFragment(getParentFragmentManager());
        });
    }
}
