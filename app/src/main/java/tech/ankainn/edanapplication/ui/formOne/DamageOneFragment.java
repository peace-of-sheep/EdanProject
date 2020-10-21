package tech.ankainn.edanapplication.ui.formOne;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentSwitchableBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class DamageOneFragment extends BindingFragment<FragmentSwitchableBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideFormOneViewModelFactory(requireContext());
        SwitchableViewModel viewModel = new ViewModelProvider(this, factory).get(SwitchableViewModel.class);

        binding().setTitle(getString(R.string.title_damage_one));
        binding().setBottomVisibility(false);
        binding().setOptionsVisibility(false);

        String[] names = getResources().getStringArray(R.array.damage_one);
        SwitchableQuanAdapter adapter = new SwitchableQuanAdapter(names);
        binding().recyclerView.setAdapter(adapter);

        viewModel.getDamageOne().observe(getViewLifecycleOwner(), selectableData ->
                adapter.submitList(selectableData.list));
    }
}
