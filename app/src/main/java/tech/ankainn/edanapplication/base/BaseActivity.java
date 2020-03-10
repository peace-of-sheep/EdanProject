package tech.ankainn.edanapplication.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.ActivityFragmentContainerBinding;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity {

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = getLayoutRes();
        if(layout == 0) {
            return;
        } else if(layout == R.layout.activity_fragment_container) {
            ActivityFragmentContainerBinding binding = ActivityFragmentContainerBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            binding.container.setOnApplyWindowInsetsListener((view, insets) -> {
                Timber.i("onCreate: %s", view);
                Timber.i("onCreate: insets[%s] %s", Integer.toHexString(System.identityHashCode(insets)), insets);
                Timber.i("onCreate: insets consumed [%s]", insets.isConsumed());
                boolean consumed = false;
                for(int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    View child = ((ViewGroup) view).getChildAt(i);
                    Timber.d("onCreate: dispatch to -> %s", child);
                    WindowInsets childResult = child.dispatchApplyWindowInsets(insets);
                    Timber.v("onCreate: childResult[%s]", Integer.toHexString(System.identityHashCode(childResult)));
                    Timber.v("onCreate: %s %s", childResult, childResult.isConsumed());
                    if(childResult.isConsumed()) {
                        consumed = true;
                    }
                }
                Timber.v("onCreate: %s", consumed);
                return consumed ? insets.consumeSystemWindowInsets() : insets;
            });
        } else {
            setContentView(layout);
        }

        if(shouldFullscreen()) {
            allowFullscreen();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    protected boolean shouldFullscreen() {
        return true;
    }

    private void allowFullscreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean lightMode = getResources().getBoolean(R.bool.light_mode);
            if(lightMode) {
                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            } else {
                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
    }
}
