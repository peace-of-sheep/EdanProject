package tech.ankainn.edanapplication.ui.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentCameraBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import timber.log.Timber;

public class CameraFragment extends BindingFragment<FragmentCameraBinding> {

    @Override
    protected FragmentCameraBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCameraBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (hasCameraPermission()) {
            setUpCamera();
        } else {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .popBackStack();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!hasCameraPermission()) {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .popBackStack();
        }
    }

    private void setUpCamera() {
        binding().previewView.post(() -> {
            ListenableFuture<ProcessCameraProvider>  cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (InterruptedException | ExecutionException e) {
                    Timber.e(e);
                }
            }, ContextCompat.getMainExecutor(requireContext()));
        });
    }

    private void bindPreview(ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build();

        ImageCapture imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(binding().previewView.getDisplay().getRotation())
                .build();

        preview.setSurfaceProvider(binding().previewView.createSurfaceProvider());

        cameraProvider.unbindAll();
        cameraProvider.bindToLifecycle(getViewLifecycleOwner(), CameraSelector.DEFAULT_BACK_CAMERA, imageCapture, preview);

        binding().btn.setOnClickListener(v -> captureImage(imageCapture));
    }

    private void captureImage(ImageCapture imageCapture) {

        long timestamp = System.currentTimeMillis();

        File parentFile = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String fileName = timestamp+"_img"+".jpg";
        File outputFile = new File(parentFile, fileName);

        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(outputFile).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(requireContext()), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                String filePath = outputFileResults.getSavedUri() != null ?
                        outputFileResults.getSavedUri().getPath() :
                        outputFile.getAbsolutePath();

                if(filePath == null) return;

                Navigation.findNavController(requireActivity(), R.id.fragment_container)
                        .navigate(CameraFragmentDirections.actionCameraToPreview(filePath));
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Toast.makeText(requireContext(), "Error taking image", Toast.LENGTH_SHORT).show();
                Timber.e(exception);
            }
        });
    }

    public boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED;
    }
}
