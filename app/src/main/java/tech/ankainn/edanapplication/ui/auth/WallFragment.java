package tech.ankainn.edanapplication.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import tech.ankainn.edanapplication.R;
import timber.log.Timber;

public class WallFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView view = new ImageView(requireContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.wall_image));
        Timber.tag("MapLocationFragment").d("onCreateView: %s", this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Timber.tag("MapLocationFragment").d("onDestroyView: %s", this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.tag("MapLocationFragment").d("onDestroy: %s", this);
    }
}
