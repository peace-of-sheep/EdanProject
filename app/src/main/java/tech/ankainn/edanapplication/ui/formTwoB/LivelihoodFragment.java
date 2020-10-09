package tech.ankainn.edanapplication.ui.formTwoB;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.databinding.FragmentFormTwoBBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class LivelihoodFragment extends BindingFragment<FragmentFormTwoBBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LivelihoodAdapter adapter = new LivelihoodAdapter(data ->
                LivelihoodDialogFragment.showFragment(getParentFragmentManager(), data.tempId),
                AppExecutors.getInstance());
        binding().recyclerView.setAdapter(adapter);

        ViewModelProvider.Factory factory = InjectorUtil.provideViewModelFactory(requireContext());
        LivelihoodViewModel viewModel = new ViewModelProvider(this, factory).get(LivelihoodViewModel.class);

        viewModel.getList().observe(getViewLifecycleOwner(), adapter::submitList);

        binding().btnAdd.setOnClickListener(v ->
                LivelihoodDialogFragment.showFragment(getParentFragmentManager()));
    }
}
