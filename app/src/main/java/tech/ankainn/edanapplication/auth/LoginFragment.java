package tech.ankainn.edanapplication.auth;

import androidx.databinding.ViewDataBinding;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentLoginBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;

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
    }
}
