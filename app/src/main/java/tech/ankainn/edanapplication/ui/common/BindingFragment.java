package tech.ankainn.edanapplication.ui.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import tech.ankainn.edanapplication.databinding.LayoutMemberBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.ViewBindingUtil;

public abstract class BindingFragment<VB extends ViewBinding> extends Fragment implements ViewFinder {

    private AutoClearedValue<VB> binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        VB viewBinding = ViewBindingUtil.inflate(getClass(), inflater, container, false);
        binding = new AutoClearedValue<>(viewBinding, getViewLifecycleOwner());
        return viewBinding.getRoot();
    }

    protected final VB binding() {
        return binding.get();
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
