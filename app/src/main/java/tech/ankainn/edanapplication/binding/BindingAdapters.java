package tech.ankainn.edanapplication.binding;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    @BindingAdapter(value = "visibleGone")
    public static void showHide(View view, boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        if(view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }
}
