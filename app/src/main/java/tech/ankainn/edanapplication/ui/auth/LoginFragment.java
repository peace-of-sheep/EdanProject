package tech.ankainn.edanapplication.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.ui.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentLoginBinding;
import tech.ankainn.edanapplication.ui.common.NavController;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class LoginFragment extends BaseFragment {

    private AutoClearedValue<FragmentLoginBinding> binding;

    @NotNull
    @Override
    protected View makeView(LayoutInflater inflater, ViewGroup container) {
        final FragmentLoginBinding bindingTemp =
                FragmentLoginBinding.inflate(inflater, container, false);
        bindingTemp.setLifecycleOwner(getViewLifecycleOwner());
        binding = new AutoClearedValue<>(bindingTemp);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.get().setViewModel(viewModel);
        Glide.with(this).load(R.drawable.wall_image).into(binding.get().wallImage);
    }
}
