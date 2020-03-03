package tech.ankainn.edanapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.ViewProgressBarHintTextBinding;

public class ProgressBarHintTextView extends FrameLayout {

    private ViewProgressBarHintTextBinding binding;

    public ProgressBarHintTextView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ProgressBarHintTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressBarHintTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ProgressBarHintTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        binding = ViewProgressBarHintTextBinding.inflate(LayoutInflater.from(context), this);
        String hintText = context.getString(R.string.default_loading);
        setHintText(hintText);
    }

    public void setHintText(String hintText) {
        binding.hintText.setText(hintText);
    }

    public void hide() {
        binding.hintTextLayout.setVisibility(GONE);
        binding.progressBar.hide();
        setVisibility(GONE);
    }

    public void show() {
        binding.hintTextLayout.setVisibility(VISIBLE);
        binding.progressBar.show();
        setVisibility(VISIBLE);
    }
}
