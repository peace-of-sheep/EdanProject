package tech.ankainn.edanapplication.ui.auth;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.ui.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentLoginBinding;
import tech.ankainn.edanapplication.ui.dialogs.InputDialogFragment;
import tech.ankainn.edanapplication.ui.dialogs.InputDialogState;
import tech.ankainn.edanapplication.ui.form.MapLocationFragment;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import timber.log.Timber;

public class LoginFragment extends BaseFragment {

    private AutoClearedValue<FragmentLoginBinding> binding;

    private LoginViewModel viewModel;

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

        binding.get().passInput.setRawInputType(InputType.TYPE_NULL);
        binding.get().passInput.setTransformationMethod(PasswordTransformationMethod.getInstance());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.get().setViewModel(viewModel);

        viewModel.getViewId().observe(getViewLifecycleOwner(), viewId -> {
            Integer state = InputDialogState.getInstance().getState().getValue();

            if(viewId != LoginViewModel.NO_VIEW && state != null && state == InputDialogState.CLOSED) {
                InputDialogFragment.create(getParentFragmentManager(), this, viewId);
            }
        });

        binding.get().btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new MapLocationFragment())
                        .commit();
            }
        });

        Timber.tag("MapLocationFragment").d("onActivityCreated: %s", this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Timber.tag("MapLocationFragment").d("onDestroyView: %s", this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.tag("MapLocationFragment").d("onDestroy: %s", this);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public View findViewById(int viewId) {
        switch (viewId) {
            case R.id.user_input: return binding.get().userInput;
            case R.id.pass_input: return binding.get().passInput;
            default: return super.findViewById(viewId);
        }
    }
}
