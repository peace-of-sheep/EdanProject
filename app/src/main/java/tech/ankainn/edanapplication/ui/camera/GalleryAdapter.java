package tech.ankainn.edanapplication.ui.camera;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Objects;

import tech.ankainn.edanapplication.databinding.LayoutItemGalleryBinding;
import tech.ankainn.edanapplication.model.media.PhotoData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter;

public class GalleryAdapter extends BindingAdapter<PhotoData, LayoutItemGalleryBinding> {

    @Override
    protected LayoutItemGalleryBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return LayoutItemGalleryBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(LayoutItemGalleryBinding binding, PhotoData photoData) {
        binding.setPhoto(photoData);
    }

    @Override
    protected boolean areItemsTheSame(PhotoData oldItem, PhotoData newItem) {
        return Objects.equals(oldItem.filePath, newItem.filePath);
    }

    @Override
    protected boolean areContentsTheSame(PhotoData oldItem, PhotoData newItem) {
        return Objects.equals(oldItem.description, newItem.description);
    }
}
