package tech.ankainn.edanapplication.ui.hostmain;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import tech.ankainn.edanapplication.databinding.LayoutOptionsFilesBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;

import static tech.ankainn.edanapplication.ui.hostmain.FileOptionsDialogFragment.Option.CANCEL;
import static tech.ankainn.edanapplication.ui.hostmain.FileOptionsDialogFragment.Option.UPLOAD;
import static tech.ankainn.edanapplication.ui.hostmain.FileOptionsDialogFragment.Option.OPEN;

public class FileOptionsDialogFragment extends BottomSheetDialogFragment {

    private AutoClearedValue<LayoutOptionsFilesBinding> binding;

    public static void showFragment(Fragment targetFragment) {
        DialogFragment fragment = new FileOptionsDialogFragment();
        fragment.setTargetFragment(targetFragment, 0);
        fragment.show(targetFragment.getParentFragmentManager(), "options");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutOptionsFilesBinding binding = LayoutOptionsFilesBinding.inflate(inflater, container, false);
        this.binding = new AutoClearedValue<>(binding, getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Fragment fragment = getTargetFragment();
        if(fragment instanceof OptionCallback) {
            binding.get().btnUpload.setOnClickListener(v -> {
                ((OptionCallback) fragment).onOption(UPLOAD);
                dismiss();
            });
            binding.get().btnOpen.setOnClickListener(v -> {
                ((OptionCallback) fragment).onOption(OPEN);
                dismiss();
            });
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        Fragment fragment = getTargetFragment();
        if(fragment instanceof OptionCallback) {
            ((OptionCallback) fragment).onOption(CANCEL);
        }
    }

    public interface OptionCallback {
        void onOption(Option option);
    }

    public enum Option {
        CANCEL,
        UPLOAD,
        OPEN
    }
}
