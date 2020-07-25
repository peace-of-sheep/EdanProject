package tech.ankainn.edanapplication.ui.formTwo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentMembersBinding;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class MembersFragment extends BindingFragment<FragmentMembersBinding> implements MemberDialogFragment.MemberListener {

    private MembersViewModel viewModel;
    private AutoClearedValue<MembersAdapter> adapter;

    @Override
    protected FragmentMembersBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMembersBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
        ViewModelProvider viewModelProvider = new ViewModelProvider(navController.getViewModelStoreOwner(R.id.form_two_host_graph));

        viewModel = viewModelProvider.get(MembersViewModel.class);
        adapter = new AutoClearedValue<>(new MembersAdapter(), getViewLifecycleOwner());

        binding().recyclerView.setAdapter(adapter.get());

        binding().setTemp(viewModel.getTempHead());

        binding().familyHead.btnSave.setOnClickListener(v -> {
            if(viewModel.isHouseholdHedDataComplete()) {
                viewModel.addHouseholdHead();
            } else {
                Toast.makeText(requireContext(), "Completar datos", Toast.LENGTH_SHORT).show();
            }
        });

        binding().layoutHead.cardView.setOnClickListener(v -> {

        });

        binding().btnAddFamily.setOnClickListener(v -> MemberDialogFragment.showFragment(this));

        viewModel.getHouseholdHead().observe(getViewLifecycleOwner(), member -> {
            binding().setHouseholdHead(member);
        });
        viewModel.getList().observe(getViewLifecycleOwner(), list -> {
            adapter.get().replace(list);
        });
    }

    @Override
    public void setMember(MemberData member) {
        viewModel.addHouseholdMember(member);
    }
}
