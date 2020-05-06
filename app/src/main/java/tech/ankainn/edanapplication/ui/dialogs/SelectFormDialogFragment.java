package tech.ankainn.edanapplication.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import tech.ankainn.edanapplication.databinding.DialogSelectFormBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class SelectFormDialogFragment extends DialogFragment {

    public static final String TAG = "select_form";

    private AutoClearedValue<DialogSelectFormBinding> binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogSelectFormBinding bindingTemp = DialogSelectFormBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(bindingTemp);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Fragment fragment = getTargetFragment();
        if(!(fragment instanceof Listener)) {
            dismiss();
            return;
        }

        binding.get().formOne.setOnClickListener(v -> {
            dismiss();
            ((Listener) fragment).onFormChosen(1);
        });
        binding.get().formTwo.setOnClickListener(v -> {
            dismiss();
            ((Listener) fragment).onFormChosen(2);
        });
    }

    public static void create(FragmentManager fm, Fragment targetFragment) {
        SelectFormDialogFragment instance = new SelectFormDialogFragment();
        instance.setTargetFragment(targetFragment, 0);
        instance.show(fm,TAG);
    }

    public interface Listener {
        void onFormChosen(int choice);
    }
}
