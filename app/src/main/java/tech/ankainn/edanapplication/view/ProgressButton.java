package tech.ankainn.edanapplication.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.google.android.material.button.MaterialButton;

public class ProgressButton extends MaterialButton {

    private CircularProgressDrawable progressDrawable;

    private CharSequence tempText;

    public ProgressButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        makeProgressDrawable(context);
    }

    private void makeProgressDrawable(Context context) {
        progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStyle(CircularProgressDrawable.DEFAULT);

        float centerRadius = progressDrawable.getCenterRadius();
        float strokeWidth = progressDrawable.getStrokeWidth();

        int size = ((int) (centerRadius + strokeWidth)) * 2;
        progressDrawable.setBounds(0, 0, size, size);
    }

    public void showProgress(String progressText) {
        tempText = getText();

        DynamicDrawableSpan drawableSpan = new DynamicDrawableSpan() {
            @Override
            public Drawable getDrawable() {
                return progressDrawable;
            }
        };

        SpannableString spannableString = new SpannableString(progressText);
        int length = spannableString.length();
        spannableString.setSpan(drawableSpan, length - 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        progressDrawable.start();

        setText(spannableString);
    }

    public static void hideProgress(MaterialButton button, CharSequence stillText) {
        button.setText(stillText);
    }

    public static void showProgressUtil(MaterialButton button, CharSequence progressText) {

        Context context = button.getContext();

        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStyle(CircularProgressDrawable.DEFAULT);

        float centerRadius = progressDrawable.getCenterRadius();
        float strokeWidth = progressDrawable.getStrokeWidth();

        int size = ((int) (centerRadius + strokeWidth)) * 2;
        progressDrawable.setBounds(0, 0, size, size);

        DynamicDrawableSpan drawableSpan = new DynamicDrawableSpan() {
            @Override
            public Drawable getDrawable() {
                return progressDrawable;
            }
        };

        CharSequence source = progressText + "   ";

        SpannableString spannableString = new SpannableString(source);
        int length = spannableString.length();
        spannableString.setSpan(drawableSpan, length - 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        progressDrawable.start();

        progressDrawable.setCallback(new Drawable.Callback() {
            @Override
            public void invalidateDrawable(@NonNull Drawable who) {
                button.invalidate();
            }

            @Override
            public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {

            }

            @Override
            public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {

            }
        });

        button.setText(spannableString);
    }
}
