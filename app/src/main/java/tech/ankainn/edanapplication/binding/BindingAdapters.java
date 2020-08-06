package tech.ankainn.edanapplication.binding;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.bumptech.glide.Glide;

import java.util.Objects;

import tech.ankainn.edanapplication.R;

public class BindingAdapters {

    private static final int POS_TAG = 1;

    @BindingAdapter(value = "visibleGone")
    public static void showHide(View view, boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        if(view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    @BindingAdapter(value = "textDropDown")
    public static void setTextDropDown(AutoCompleteTextView view, String value) {
        String oldValue = view.getText().toString();
        if(!Objects.equals(oldValue, value)) {
            Runnable r = () -> view.setText(value, false);
            if(view.isLaidOut()) {
                r.run();
            } else {
                view.post(r);
            }
        }
    }

    @InverseBindingAdapter(attribute = "textDropDown", event = "android:textAttrChanged")
    public static String getTextDropDown(AutoCompleteTextView view) {
        return view.getText().toString();
    }

    @BindingAdapter(value = {"itemPosition", "onItemClickListener", "itemPositionAttrChanged"}, requireAll = false)
    public static void setItemPosition(AutoCompleteTextView view, Integer pos, final OnItemClickListener listener, final InverseBindingListener inverseListener) {

        if (pos != null) {
            view.setTag(R.id.autocompletetextview_pos, pos);
        }

        view.setOnItemClickListener((parent, view1, position, id) -> {

            view.setTag(R.id.autocompletetextview_pos, position);

            if (listener != null) {
                listener.onItemClick(parent, view1, position, id);
            }
            if (inverseListener != null) {
                inverseListener.onChange();
            }
        });
    }

    @InverseBindingAdapter(attribute = "itemPosition")
    public static int getItemPosition(AutoCompleteTextView view) {
        Object temp = view.getTag(R.id.autocompletetextview_pos);
        return temp != null ? (int) temp : -1;
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
