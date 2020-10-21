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
        this(context, null);
    }

    public ProgressBarHintTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarHintTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ProgressBarHintTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        binding = ViewProgressBarHintTextBinding.inflate(LayoutInflater.from(context), this);
        String hintText = context.getString(R.string.loading);
        setHintText(hintText);
    }

    @Override
    protected void onDetachedFromWindow() {
        binding = null;
        super.onDetachedFromWindow();
    }

    public void setHintText(String hintText) {
        binding.hintText.setText(hintText);
    }

    public void setShowHide(boolean show) {
        if(show) {
            setVisibility(VISIBLE);
            binding.progressBar.show();
        } else {
            setVisibility(GONE);
            binding.progressBar.hide();
        }
    }
}
