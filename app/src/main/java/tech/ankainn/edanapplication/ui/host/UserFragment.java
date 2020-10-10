package tech.ankainn.edanapplication.ui.host;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

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
    }
}
