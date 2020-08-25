package tech.ankainn.edanapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;

import tech.ankainn.edanapplication.R;

public class NavigatorView extends FrameLayout {

    public NavigatorView(@NonNull Context context) {
        this(context, null);
    }

    public NavigatorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigatorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NavigatorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);



        MaterialShapeDrawable drawable = MaterialShapeDrawable.createWithElevationOverlay(context);
        setBackground(drawable);

        float elevation = getResources().getDimension(R.dimen.design_navigator_elevation);

        setElevation(elevation);
    }

    @Override
    public void setElevation(float elevation) {
        super.setElevation(elevation);

        MaterialShapeUtils.setElevation(this, elevation);
    }
}
