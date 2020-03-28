package tech.ankainn.edanapplication.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @NonNull
    protected abstract View getViewBinding(LayoutInflater inflater, ViewGroup container);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getViewBinding(inflater, container);
        view.setFocusableInTouchMode(true);
        view.setClickable(isClickable());
        return view;
    }

    protected boolean isClickable() {
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void setupHideSoftKeyboard(View rootView) {
        Activity activity = requireActivity();

        rootView.setOnTouchListener((v, event) ->{
            View currentFocus = activity.getCurrentFocus();
            if(currentFocus instanceof EditText) {
                hideSoftKeyboard(activity, currentFocus);
            }
            return false;
        });

        if(rootView instanceof ViewGroup) {
            for(int i = 0 ; i < ((ViewGroup) rootView).getChildCount(); i++) {
                View child = ((ViewGroup) rootView).getChildAt(i);
                if( !(child instanceof EditText)) {
                    child.setOnTouchListener((v, event) -> {
                        View currentFocus = activity.getCurrentFocus();
                        if(currentFocus instanceof EditText) {
                            hideSoftKeyboard(activity, currentFocus);
                        }
                        return false;
                    });
                }
            }
        }
    }

    protected void hideSoftKeyboard(@NonNull Activity activity, @NonNull View view) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
