package tech.ankainn.edanapplication.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements ViewFinder {

    @NonNull
    protected abstract View makeView(LayoutInflater inflater, ViewGroup container);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = makeView(inflater, container);
        view.setFocusableInTouchMode(true);
        view.setClickable(isClickable());
        return view;
    }

    protected boolean isClickable() {
        return true;
    }

    protected void hideSoftKeyboard(@NonNull Activity activity, @NonNull View view) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Nullable
    @Override
    public View rootView() {
        return getView();
    }
}
