package tech.ankainn.edanapplication.ui.geninfo;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.databinding.FragmentExtraBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class ExtraFragment extends BindingFragment<FragmentExtraBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        boolean vis = ExtraFragmentArgs.fromBundle(requireArguments()).getVisibility();
        binding().setVisibility(vis);

        ViewModelProvider.Factory factory = InjectorUtil.provideViewModelFactory(requireContext());
        ExtraViewModel viewModel = new ViewModelProvider(this, factory).get(ExtraViewModel.class);

        binding().textLocality.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setLocality(requireContext(), pos);
        });
        binding().textBsu.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setBSU(requireContext(), pos);
        });
        binding().textCa.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setCA(requireContext(), pos);
        });
        binding().textCm.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setCM(requireContext(), pos);
        });

        viewModel.getExtraData().observe(getViewLifecycleOwner(), extraData -> {
            binding().setExtraData(extraData);
        });
    }
}
