package tech.ankainn.edanapplication.ui.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tech.ankainn.edanapplication.ui.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentNewFormsBinding;
import tech.ankainn.edanapplication.ui.common.NavController;
import tech.ankainn.edanapplication.ui.dialogs.SelectFormDialogFragment;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class NewFormsFragment extends BaseFragment implements SelectFormDialogFragment.Listener {

    private AutoClearedValue<FragmentNewFormsBinding> binding;

    @NonNull
    @Override
    protected View makeView(LayoutInflater inflater, ViewGroup container) {
        FragmentNewFormsBinding bindingTemp = FragmentNewFormsBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(bindingTemp);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.get().frameAdd.setOnClickListener(v -> SelectFormDialogFragment.create(getParentFragmentManager(), this));
    }

    @Override
    public void onFormChosen(int choice) {
        if(choice == 1) {
            NavController.openSwitchable(requireActivity(), 0);
        } else if(choice == 2) {
            NavController.openHouseholdInfo(requireActivity());
        }
    }
}
