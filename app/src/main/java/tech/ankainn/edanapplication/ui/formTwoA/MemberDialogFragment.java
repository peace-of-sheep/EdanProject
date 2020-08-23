package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import tech.ankainn.edanapplication.databinding.LayoutMemberBinding;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class MemberDialogFragment extends BottomSheetDialogFragment {

    private static final String ID_KEY = "id_key";

    private AutoClearedValue<LayoutMemberBinding> binding;

    public static void showFragment(FragmentManager fm) {
        DialogFragment fragment = new MemberDialogFragment();
        fragment.show(fm, "add");
    }

    public static void showFragment(FragmentManager fm, long tempId) {
        Bundle args = new Bundle();
        args.putLong(ID_KEY, tempId);

        DialogFragment fragment = new MemberDialogFragment();
        fragment.setArguments(args);
        fragment.show(fm, "add");
    }

    private static long getIdFromBundle(Bundle args) {
        if (args == null) {
            return 0L;
        } else {
            return args.getLong(ID_KEY, 0L);
        }
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

        ViewModelStoreOwner owner = ScopeNavHostFragment.getOwner(this);
        ViewModelProvider.Factory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        MembersViewModel viewModel = new ViewModelProvider(owner, factory).get(MembersViewModel.class);

        long tempId = getIdFromBundle(getArguments());
        viewModel.searchTempId(tempId);

        binding.get().setCancelVisibility(true);

        viewModel.getDialogMemberData().observe(getViewLifecycleOwner(), memberData ->
                binding.get().setMember(memberData));

        binding.get().btnSave.setOnClickListener(v -> {
            if (binding.get().getMember().notEmpty()) {
                viewModel.pushActiveMemberData();
                dismiss();
            } else {
                // todo redo
                Toast.makeText(requireContext(), "More content", Toast.LENGTH_SHORT).show();
            }
        });
        binding.get().btnCancel.setOnClickListener(v -> dismiss());
    }
}
