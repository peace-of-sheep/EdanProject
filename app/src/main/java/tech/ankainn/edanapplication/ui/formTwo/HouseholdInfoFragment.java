package tech.ankainn.edanapplication.ui.formTwo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tech.ankainn.edanapplication.databinding.FragmentHouseholdInfoBinding;
import tech.ankainn.edanapplication.ui.base.BaseFragment;
import tech.ankainn.edanapplication.ui.common.NavController;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class HouseholdInfoFragment extends BaseFragment {

    private AutoClearedValue<FragmentHouseholdInfoBinding> binding;

    @NonNull
    @Override
    protected View makeView(LayoutInflater inflater, ViewGroup container) {
        FragmentHouseholdInfoBinding bindingTemp = FragmentHouseholdInfoBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(bindingTemp);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.get().btnNext.setOnClickListener(v -> NavController.openNewForms(requireActivity()));
    }
}
