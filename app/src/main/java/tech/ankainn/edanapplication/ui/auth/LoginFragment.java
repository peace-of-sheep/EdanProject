package tech.ankainn.edanapplication.ui.auth;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.ui.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentLoginBinding;
import tech.ankainn.edanapplication.ui.bottomsheets.InputDialogFragment;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class LoginFragment extends BaseFragment implements InputDialogFragment.Listener{

    private AutoClearedValue<FragmentLoginBinding> binding;

    private LoginViewModel viewModel;
    private AuthViewModel sharedViewModel;

    private TextView tempTextView;

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

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        binding.get().passInput.setRawInputType(InputType.TYPE_NULL);
        binding.get().passInput.setTransformationMethod(PasswordTransformationMethod.getInstance());

        binding.get().setViewModel(viewModel);

        viewModel.viewId.observe(getViewLifecycleOwner(), viewId -> {
            if(viewId != LoginViewModel.NO_VIEW) {
                tempTextView = (TextView) findViewById(viewId);

                boolean opened = InputDialogFragment.Util.showInputDialog(getParentFragmentManager(),
                        tempTextView, this);

                tempTextView = opened ? tempTextView : null;
            }
        });
    }

    @Override
    public View findViewById(int viewId) {
        switch (viewId) {
            case R.id.user_input: return binding.get().userInput;
            case R.id.pass_input: return binding.get().passInput;
            default: return super.findViewById(viewId);
        }
    }


    @Nullable
    @Override
    public TextView getViewListener() {
        return tempTextView;
    }
}
