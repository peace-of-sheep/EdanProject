package tech.ankainn.edanapplication.ui.camera;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.databinding.LayoutItemGalleryBinding;
import tech.ankainn.edanapplication.model.app.media.PhotoData;
import tech.ankainn.edanapplication.ui.common.BindingAdapter;

public class GalleryAdapter extends BindingAdapter<PhotoData, LayoutItemGalleryBinding> {

    public GalleryAdapter(AppExecutors appExecutors) {
        super(appExecutors, new DiffUtil.ItemCallback<PhotoData>() {
            @Override
            public boolean areItemsTheSame(@NotNull PhotoData oldItem, @NotNull PhotoData newItem) {
                return Objects.equals(oldItem.filePath, newItem.filePath);
            }

            @Override
            public boolean areContentsTheSame(@NotNull PhotoData oldItem, @NotNull PhotoData newItem) {
                return Objects.equals(oldItem.description, newItem.description);
            }
        });
    }

    @Override
    protected LayoutItemGalleryBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return LayoutItemGalleryBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(LayoutItemGalleryBinding binding, PhotoData photoData) {
        binding.setPhoto(photoData);
    }
}
