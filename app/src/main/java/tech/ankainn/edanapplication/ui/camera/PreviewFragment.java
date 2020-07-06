package tech.ankainn.edanapplication.ui.camera;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.io.File;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentPreviewBinding;
import tech.ankainn.edanapplication.ui.base.BindingFragment;
import tech.ankainn.edanapplication.util.OnAfterTextChanged;

public class PreviewFragment extends BindingFragment<FragmentPreviewBinding> {

    @Override
    protected FragmentPreviewBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentPreviewBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String filePath = PreviewFragmentArgs.fromBundle(requireArguments()).getFilePath();

        Glide.with(this).load(filePath).into(binding().img);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                File file = new File(filePath);
                if (file.delete()) {
                    Navigation.findNavController(requireActivity(), R.id.fragment_container).popBackStack();
                    Toast.makeText(requireContext(), getString(R.string.delete), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), getString(R.string.back), Toast.LENGTH_SHORT).show();
                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        binding().layoutDescription.post(() -> {
            int defaultColor = binding().layoutDescription.getDefaultHintTextColor().getDefaultColor();

            binding().textDescription.addTextChangedListener(new OnAfterTextChanged() {
                @Override
                public void afterTextChanged(Editable s) {
                    binding().layoutDescription.getEndIconDrawable().setTint(s.length() > 0 ? ContextCompat.getColor(requireContext(), R.color.colorAccent) : defaultColor);
                }
            });
        });

        binding().layoutDescription.setEndIconOnClickListener(v -> {
            Toast.makeText(requireContext(), filePath, Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireActivity(), R.id.fragment_container).popBackStack();
        });
    }
}
