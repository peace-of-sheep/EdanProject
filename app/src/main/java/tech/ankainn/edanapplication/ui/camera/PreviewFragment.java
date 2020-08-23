package tech.ankainn.edanapplication.ui.camera;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.io.File;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentPreviewBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.OnAfterTextChanged;

public class PreviewFragment extends BindingFragment<FragmentPreviewBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
        ViewModelProvider viewModelProvider = new ViewModelProvider(navController.getViewModelStoreOwner(R.id.camera_graph));

        GalleryViewModel viewModel = viewModelProvider.get(GalleryViewModel.class);

        String filePath = PreviewFragmentArgs.fromBundle(requireArguments()).getFilePath();

        File file = new File(filePath);
        if(!file.exists()) {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).popBackStack();
            return;
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
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
            viewModel.addPhoto(filePath, binding().textDescription.getText().toString());
            Navigation.findNavController(requireActivity(), R.id.fragment_container).popBackStack();
        });

        Glide.with(this).load(file).into(binding().img);
    }
}
