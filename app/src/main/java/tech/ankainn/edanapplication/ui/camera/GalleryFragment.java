package tech.ankainn.edanapplication.ui.camera;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentGalleryBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;

public class GalleryFragment extends BindingFragment<FragmentGalleryBinding> {

    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(GalleryFragmentDirections.actionGalleryToCamera());
        }

        @Override
        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
            Toast.makeText(requireContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
            permissionToken.continuePermissionRequest();
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
        ViewModelProvider viewModelProvider = new ViewModelProvider(navController.getViewModelStoreOwner(R.id.camera_graph));

        GalleryViewModel viewModel = viewModelProvider.get(GalleryViewModel.class);

        GalleryAdapter adapter = new GalleryAdapter(AppExecutors.getInstance());
        binding().recyclerView.setAdapter(adapter);

        binding().setEmptyVisibility(viewModel.isEmpty());

        viewModel.getList().observe(getViewLifecycleOwner(), list -> {
            binding().setEmptyVisibility(viewModel.isEmpty());
            adapter.submitList(list);
        });

        binding().btnAddPhoto.setOnClickListener(v ->
                Dexter.withContext(requireContext())
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(permissionListener)
                        .check());
    }
}
