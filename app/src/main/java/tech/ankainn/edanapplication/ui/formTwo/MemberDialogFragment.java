package tech.ankainn.edanapplication.ui.formTwo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import tech.ankainn.edanapplication.databinding.LayoutAddFamilyBinding;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class MemberDialogFragment extends BottomSheetDialogFragment {

    private AutoClearedValue<LayoutAddFamilyBinding> binding;

    public static void showFragment(Fragment targetFragment) {
        DialogFragment fragment = new MemberDialogFragment();
        fragment.setTargetFragment(targetFragment, 0);
        fragment.show(targetFragment.getParentFragmentManager(), "add");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutAddFamilyBinding viewBinding = LayoutAddFamilyBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(viewBinding, getViewLifecycleOwner());
        return viewBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MemberData memberData = new MemberData();
        memberData.head = false;

        binding.get().setCancelVisibility(true);

        binding.get().setMember(memberData);

        binding.get().btnSave.setOnClickListener(v -> {
            if(!memberData.checkData()) {
                // TODO cambiar texto
                Toast.makeText(requireContext(), "Completar datos", Toast.LENGTH_SHORT).show();
            } else {
                Fragment fragment = getTargetFragment();
                if(fragment instanceof MemberListener) {
                    ((MemberListener) fragment).setMember(memberData);
                }
                dismiss();
            }
        });
        binding.get().btnCancel.setOnClickListener(v -> dismiss());
    }

    public interface MemberListener {
        void setMember(MemberData member);
    }
}
