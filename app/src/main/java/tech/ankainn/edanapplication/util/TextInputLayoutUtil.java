package tech.ankainn.edanapplication.util;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

/**
 * Utility for TextInputLayout click listener
 */
public final class TextInputLayoutUtil {

    @SuppressLint("ClickableViewAccessibility")
    public static void setOnClickListener(TextInputLayout layout, View.OnClickListener listener) {
        EditText editText = layout.getEditText();

        if (editText == null)
            throw new RuntimeException("Must implement a EditText inside!");

        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                listener.onClick(v);
                v.performClick();
            }
            return false;
        });

        if (layout.getEndIconMode() != TextInputLayout.END_ICON_NONE) {
            editText.setOnFocusChangeListener((v, hasFocus) -> layout.setEndIconActivated(hasFocus));
        }
    }
}
