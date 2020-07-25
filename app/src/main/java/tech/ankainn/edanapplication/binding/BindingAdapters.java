package tech.ankainn.edanapplication.binding;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import tech.ankainn.edanapplication.R;

public class BindingAdapters {
    @BindingAdapter(value = "visibleGone")
    public static void showHide(View view, boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        if(view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    @BindingAdapter(value = "dropdown")
    public static void setDropdown(AutoCompleteTextView textView, String[] array) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        textView.getContext(),
                        R.layout.layout_dropdown_menu_item,
                        array);
        textView.setAdapter(adapter);
    }

    @BindingAdapter(value = "imageUri")
    public static void setImageUri(ImageView imageView, String uri) {
        Context context = imageView.getContext().getApplicationContext();
        Glide.with(context).load(uri).into(imageView);
    }
}
