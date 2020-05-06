package tech.ankainn.edanapplication.ui.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        FragmentNewFormsBinding binding = FragmentNewFormsBinding.inflate(inflater, container, false);
        this.binding = new AutoClearedValue<>(binding);
        return binding.getRoot();
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
            Toast.makeText(getContext(), "Open Form Two", Toast.LENGTH_SHORT).show();
        }
    }
}
