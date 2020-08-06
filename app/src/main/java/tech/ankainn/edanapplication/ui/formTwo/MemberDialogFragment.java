package tech.ankainn.edanapplication.ui.formTwo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.LayoutMemberBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;

import static tech.ankainn.edanapplication.util.NavigationUtil.getViewModelProvider;

public class MemberDialogFragment extends BottomSheetDialogFragment {

    private AutoClearedValue<LayoutMemberBinding> binding;

    public static void showFragment(FragmentManager fm) {
        DialogFragment fragment = new MemberDialogFragment();
        fragment.show(fm, "add");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutMemberBinding viewBinding = LayoutMemberBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(viewBinding, getViewLifecycleOwner());
        return viewBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavBackStackEntry owner = Navigation
                .findNavController(requireActivity(), R.id.fragment_container)
                .getBackStackEntry(R.id.form_two_host_fragment);
        FormTwoViewModel viewModel = new ViewModelProvider(owner).get(FormTwoViewModel.class);

        binding.get().setCancelVisibility(true);

        viewModel.getActiveMemberData().observe(getViewLifecycleOwner(), memberData -> {
            binding.get().setMember(memberData);
        });

        binding.get().btnSave.setOnClickListener(v -> {
            if (binding.get().getMember().notEmpty()) {
                viewModel.pushActiveMemberData();
                dismiss();
            } else {
                // todo redo
                Toast.makeText(requireContext(), "More content", Toast.LENGTH_SHORT).show();
            }
        });
        binding.get().btnCancel.setOnClickListener(v -> {
            dismiss();
        });
    }
}
