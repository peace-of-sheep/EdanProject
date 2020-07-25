package tech.ankainn.edanapplication.ui.hostmain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentHostMainBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;

public class HostMainFragment extends BindingFragment<FragmentHostMainBinding> {

    @Override
    protected FragmentHostMainBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHostMainBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding().mainFragmentContainer.post(() -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.main_fragment_container);
            NavigationUI.setupWithNavController(binding().bnv, navController);
        });
    }
}
