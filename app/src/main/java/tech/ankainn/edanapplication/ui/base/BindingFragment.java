package tech.ankainn.edanapplication.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import tech.ankainn.edanapplication.util.AutoClearedValue;

public abstract class BindingFragment<VB extends ViewBinding> extends BaseFragment {

    private AutoClearedValue<VB> binding;

    @NonNull
    @Override
    protected final View makeView(LayoutInflater inflater, ViewGroup container) {
        VB viewBinding = makeBinding(inflater, container);
        binding = new AutoClearedValue<>(viewBinding);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);
        return viewBinding.getRoot();
    }

    protected final VB binding() {
        return binding.get();
    }

    protected abstract VB makeBinding(LayoutInflater inflater, ViewGroup container);
}
