package tech.ankainn.edanapplication.ui.host;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentUserBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class UserFragment extends BindingFragment<FragmentUserBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideViewModelFactory(requireContext());
        UserViewModel viewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);

        viewModel.getUserData().observe(getViewLifecycleOwner(), userData -> binding().setUser(userData));

        viewModel.getStatus().observe(getViewLifecycleOwner(), state -> {
            if (state == UserViewModel.State.LOGOUT) {
                NavDirections action = HostFragmentDirections.actionHostToLogin();
                Navigation.findNavController(requireActivity(), R.id.fragment_container)
                        .navigate(action);
            }
        });

        binding().btnLogout.setOnClickListener(v -> viewModel.logout());
    }
}
