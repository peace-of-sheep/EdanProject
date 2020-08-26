package tech.ankainn.edanapplication.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.binding.BindingAdapters;
import tech.ankainn.edanapplication.databinding.ViewNavigatorBinding;
import tech.ankainn.edanapplication.databinding.ViewNavigatorItemBinding;
import tech.ankainn.edanapplication.ui.common.BindingAdapter;

public class NavigatorView extends LinearLayout {

    private int[] destinationsId;

    private int current = 0;

    private ViewNavigatorBinding binding;

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

        Resources res = getResources();

        setOrientation(HORIZONTAL);

        float elevation = getResources().getDimension(R.dimen.design_navigator_elevation);
        MaterialShapeDrawable drawable = MaterialShapeDrawable.createWithElevationOverlay(context, elevation);
        setBackground(drawable);

        binding = ViewNavigatorBinding.inflate(LayoutInflater.from(context), this);

        Drawable icBack = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_24);
        Drawable icForward = ContextCompat.getDrawable(context, R.drawable.ic_arrow_forward_24);

        String strBack = res.getString(R.string.back);
        String strForward = res.getString(R.string.next);

        binding.back.icon.setBackgroundDrawable(icBack);
        binding.back.label.setText(strBack);

        binding.forward.icon.setBackgroundDrawable(icForward);
        binding.forward.label.setText(strForward);

        int paddingHorizontal = res.getDimensionPixelSize(R.dimen.dim_sma);

        setPadding(paddingHorizontal, 0, paddingHorizontal, 0);

        setSaveEnabled(true);
    }

    public void setDestinations(int[] destinationsId) {
        this.destinationsId = destinationsId;
    }

    public void addItemView(NavigatorItem navigatorItem) {
        ViewGroup menu = binding.menu;

        ViewNavigatorItemBinding tempBind =
                ViewNavigatorItemBinding.inflate(LayoutInflater.from(getContext()), menu, true);

        tempBind.icon.setBackgroundResource(navigatorItem.drawableRes);
        tempBind.label.setText(navigatorItem.strRes);

        tempBind.getRoot().setOnClickListener(navigatorItem.listener);

        navigatorItem.setView(tempBind.label);
    }

    public void setOnBackListener(OnNavigatorListener listener) {
        binding.back.getRoot().setOnClickListener(v -> listener.onDestination(getPreviousDestination()));
    }

    public void setOnForwardListener(OnNavigatorListener listener) {
        binding.forward.getRoot().setOnClickListener(v -> listener.onDestination(getNextDestination()));
    }

    private int getPreviousDestination() {
        current--;
        if (current < 0) {
            current = destinationsId.length - 1;
        }

        return destinationsId[current];
    }

    private int getNextDestination() {
        current++;
        if (current == destinationsId.length) {
            current = 0;
        }

        return destinationsId[current];
    }

    public void showHideArrows(boolean show) {
        BindingAdapters.showHide(binding.back.getRoot(), show);
        BindingAdapters.showHide(binding.forward.getRoot(), show);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        current = savedState.currentSaved;
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentSaved = current;
        return savedState;
    }

    private static class SavedState extends BaseSavedState {

        int currentSaved;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            currentSaved = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(currentSaved);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    public interface OnNavigatorListener {
        void onDestination(int destinationId);
    }
}
