package tech.ankainn.edanapplication.ui.formOne;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.Arrays;
import java.util.List;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentSwitchableBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class DamageThreeFragment extends BindingFragment<FragmentSwitchableBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideFormOneViewModelFactory(requireContext());
        SwitchableViewModel viewModel = new ViewModelProvider(this, factory).get(SwitchableViewModel.class);

        binding().setTitle(getString(R.string.title_damage_three));
        binding().setBottomVisibility(false);
        binding().setOptionsVisibility(false);

        String[] names = getResources().getStringArray(R.array.damage_three);
        SwitchableAdapter adapter = new SwitchableAdapter(names);
        binding().recyclerView.setAdapter(adapter);

        viewModel.getDamageThree().observe(getViewLifecycleOwner(), selectableData ->
                adapter.submitList(selectableData.list));
    }
}