package tech.ankainn.edanapplication.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

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
        binding.getRoot().setClickable(true);
        binding.getRoot().setFocusable(true);
        binding.getRoot().setFitsSystemWindows(true);
        return binding.getRoot();
    }
}
