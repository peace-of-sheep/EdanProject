package tech.ankainn.edanapplication.ui.hostmain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.LayoutSelectFormBinding;
import tech.ankainn.edanapplication.ui.hostmain.HostMainFragmentDirections;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class SelectFormDialogFragment extends BottomSheetDialogFragment {

    private AutoClearedValue<LayoutSelectFormBinding> binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutSelectFormBinding binding = LayoutSelectFormBinding.inflate(inflater, container, false);
        this.binding = new AutoClearedValue<>(binding, getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        drawablesColor();

        binding.get().formOne.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(HostMainFragmentDirections.actionMainToFormOne());
            dismissAllowingStateLoss();
        });

        binding.get().formTwo.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(HostMainFragmentDirections.actionMainToFormTwo());
            dismissAllowingStateLoss();
        });
    }

    private void drawablesColor() {
        binding.get().getRoot().post(() -> {
            int color = binding.get().formTwo.getCurrentTextColor();
            binding.get().formTwo.getCompoundDrawablesRelative()[0].mutate().setTint(color);
            binding.get().formOne.getCompoundDrawablesRelative()[0].mutate().setTint(color);
        });
    }
}
