package tech.ankainn.edanapplication.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import timber.log.Timber;

public abstract class BaseFragment extends Fragment {

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void setViewDataBinding(ViewDataBinding binding);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = getLayoutRes();
        if(layout == 0) throw new RuntimeException("Must inflate a correct layout");
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layout, container, false);
        setViewDataBinding(binding);
        binding.getRoot().setFocusableInTouchMode(true);
        binding.getRoot().setClickable(isClickable());
        /*binding.getRoot().setFitsSystemWindows(shouldFitsSystemWindows());*/
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                Timber.tag("BaseActivityLog").e("onViewCreated: view[%s]", v);
                Timber.tag("BaseActivityLog").e("onViewCreated: fitsSystem %s", v.getFitsSystemWindows());
                Timber.tag("BaseActivityLog").e("onViewCreated: insets[%s] %s", Integer.toHexString(System.identityHashCode(insets)), insets);
                Timber.tag("BaseActivityLog").e("onViewCreated: insets consumed[%s]", insets.isConsumed());

                if(shouldFitsSystemWindows() && insets.hasSystemWindowInsets()) {
                    v.setPadding(insets.getSystemWindowInsetLeft(),
                            insets.getSystemWindowInsetTop(),
                            insets.getSystemWindowInsetRight(),
                            insets.getSystemWindowInsetBottom());
                    WindowInsets in = insets.consumeStableInsets().consumeSystemWindowInsets();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        in = in.consumeDisplayCutout();
                    }
                    Timber.tag("BaseActivityLog").w("onViewCreated: returned insets[%s] %s", Integer.toHexString(System.identityHashCode(in)), in);
                    Timber.tag("BaseActivityLog").w("onViewCreated: returned insets consumed[%s]", in.isConsumed());
                    return in;
                }
                return insets;
            }
        });
    }

    protected boolean isClickable() {
        return true;
    }

    protected boolean shouldFitsSystemWindows() {
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void setupHideSoftKeyboard(View rootView) {
        Activity activity = requireActivity();

        rootView.setOnTouchListener((v, event) -> {
            View currentFocus = activity.getCurrentFocus();
            if(currentFocus instanceof EditText) {
                hideSoftKeyboard(activity, currentFocus);
            }
            return false;
        });
    }

    protected void hideSoftKeyboard(@NonNull Activity activity, @NonNull View view) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
