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

public class DamageTwoFragment extends BindingFragment<FragmentSwitchableBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelStoreOwner owner = ScopeNavHostFragment.getOwner(this);
        ViewModelProvider.Factory factory = InjectorUtil.provideFormOneViewModelFactory(requireContext());
        SwitchableViewModel viewModel = new ViewModelProvider(owner, factory).get(SwitchableViewModel.class);

        binding().setTitle(getString(R.string.title_damage_two));
        binding().setBottomVisibility(false);

        List<String> names = Arrays.asList(getResources().getStringArray(R.array.damage_two));
        SwitchableAdapter adapter = new SwitchableAdapter(names);
        binding().recyclerView.setAdapter(adapter);

        viewModel.getDamageTwo().observe(getViewLifecycleOwner(), selectableData ->
                adapter.submitList(selectableData.list));
    }
}
