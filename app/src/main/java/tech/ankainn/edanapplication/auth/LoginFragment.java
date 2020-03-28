package tech.ankainn.edanapplication.auth;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentLoginBinding;
import tech.ankainn.edanapplication.textinput.TextInputFragment;
import tech.ankainn.edanapplication.textinput.TextInputViewModel;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import timber.log.Timber;

public class LoginFragment extends BaseFragment {

    private static final String DIALOG_FRAG = "dialog";

    private AutoClearedValue<FragmentLoginBinding> binding;
    private LoginViewModel viewModel;
    private SharedAuthViewModel sharedViewModel;
    private TextInputViewModel inputViewModel;

    private EditText lastViewFocused;

    @NotNull
    @Override
    protected View getViewBinding(LayoutInflater inflater, ViewGroup container) {
        final FragmentLoginBinding bindingTemp =
                DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding = new AutoClearedValue<>(bindingTemp);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedAuthViewModel.class);
        inputViewModel = new ViewModelProvider(requireActivity()).get(TextInputViewModel.class);

        binding.get().passInput.setInputType(InputType.TYPE_NULL);
        binding.get().passInput.setTransformationMethod(PasswordTransformationMethod.getInstance());

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading) {
                binding.get().progressText.show();
            } else {
                binding.get().progressText.hide();
            }
        });
        viewModel.isFocusable().observe(getViewLifecycleOwner(), this::setUpInputViewFocus);

        inputViewModel.getInput().observe(getViewLifecycleOwner(), data -> {
            Timber.i("onActivityCreated: %s", data);
            if(data.viewId == TextInputViewModel.NO_VIEW) {
                if(lastViewFocused != null) {
                    lastViewFocused.clearFocus();
                    lastViewFocused = null;
                }
                viewModel.setFocusableView(true);
            } else if(lastViewFocused != null) {
                lastViewFocused.setText(data.text);
            }
        });
    }

    private void onFocusEditText(View view, boolean hasFocus) {
        if(hasFocus) {
            boolean success = inputViewModel.tryOpenInputFragment(view.getId());
            if(success) {
                lastViewFocused = (EditText) view;
                boolean hide = view.equals(binding.get().passInput);

                TextInputFragment inputFragment = new TextInputFragment();
                inputFragment.setHidePassword(hide);
                inputFragment.setCurrentText(lastViewFocused.getText());
                inputFragment.show(getParentFragmentManager(), DIALOG_FRAG);

                setUpInputViewFocus(false);
            }
        }
    }

    private void setUpInputViewFocus(boolean isFocusable) {
        binding.get().userInput.setOnFocusChangeListener(isFocusable ? this::onFocusEditText : null);
        binding.get().passInput.setOnFocusChangeListener(isFocusable ? this::onFocusEditText : null);
    }

    /*private void sendCredentials() {
        Editable tempUser = binding.get().userInput.getText();
        Editable tempPass = binding.get().passInput.getText();

        if(tempUser != null && tempPass != null) {
            navigateToPassAuth();
        }
    }

    private void navigateToPassAuth() {

    }*/

    // TODO: 27/03/2020
    /*binding.get().passInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if(actionId == EditorInfo.IME_ACTION_SEND) {
                hideSoftKeyboard(requireActivity(), v);

                binding.get().passInput.setEnabled(false);
                binding.get().userInput.setEnabled(false);

                sendCredentials();
                handled = true;
            }
            return handled;
        });*/
}
