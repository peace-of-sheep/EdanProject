package tech.ankainn.edanapplication.ui.host;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentHostBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;

import static tech.ankainn.edanapplication.util.NavigationUtil.getNavController;

public class HostFragment extends BindingFragment<FragmentHostBinding> {

    @Override
    protected FragmentHostBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHostBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavController navController = getNavController(getChildFragmentManager(), R.id.host_fragment_container);
        NavigationUI.setupWithNavController(binding().bnv, navController);
    }
}
