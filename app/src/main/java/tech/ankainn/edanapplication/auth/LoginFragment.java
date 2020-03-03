package tech.ankainn.edanapplication.auth;

import android.os.Bundle;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentLoginBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.OnAfterTextChanged;

public class LoginFragment extends BaseFragment {

    private AutoClearedValue<FragmentLoginBinding> binding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        this.binding = new AutoClearedValue<>((FragmentLoginBinding) binding);
        getViewLifecycleOwner().getLifecycle().addObserver(this.binding);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoginViewModel viewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);

        setupHideSoftKeyboard(binding.get().getRoot());

        binding.get().userInput.addTextChangedListener(new OnAfterTextChanged() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.changeUserInput(s);
            }
        });

        binding.get().passInput.addTextChangedListener(new OnAfterTextChanged() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.changePassInput(s);
            }
        });

        binding.get().passInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if(actionId == EditorInfo.IME_ACTION_SEND) {
                viewModel.loadUser();
                hideSoftKeyboard(requireActivity(), v);
                binding.get().getRoot().requestFocus();
                handled = true;
            }
            return handled;
        });

        viewModel.getLoading().observe(getViewLifecycleOwner(),
                        isLoading -> {
            if(isLoading) {
                binding.get().progressText.show();
            } else {
                binding.get().progressText.hide();
            }
        });
    }
}