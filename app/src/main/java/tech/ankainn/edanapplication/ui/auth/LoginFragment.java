package tech.ankainn.edanapplication.ui.auth;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentLoginBinding;
import tech.ankainn.edanapplication.model.api.auth.AuthCredentials;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.view.ProgressButton;

public class LoginFragment extends BindingFragment<FragmentLoginBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideViewModelFactory(requireContext());
        LoginViewModel viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        viewModel.getState().observe(getViewLifecycleOwner(), state -> {
            if (state == LoginViewModel.State.LOADING) {
                ProgressButton.showProgressUtil(binding().btnLogin, getString(R.string.loading));
            } else {
                ProgressButton.hideProgress(binding().btnLogin, "Ingresar");
            }

            if (state == LoginViewModel.State.ERROR) {
                binding().setTextError(getString(R.string.user_not_found));
            } else if (state == LoginViewModel.State.NO_USER) {
                binding().setTextError(getString(R.string.no_username));
            } else if (state == LoginViewModel.State.NO_PASS) {
                binding().setTextError(getString(R.string.no_password));
            }
        });

        viewModel.getSingleEvent().observe(getViewLifecycleOwner(), event -> {
            if (event == LoginViewModel.State.SUCCESSFUL) {
                Navigation.findNavController(requireActivity(), R.id.fragment_container)
                        .navigate(LoginFragmentDirections.actionLoginToMain());
            } else if (event == LoginViewModel.State.ERROR) {
                Toast.makeText(requireContext(), getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
            }
        });

        binding().btnLogin.setOnClickListener(v -> {
            String user = binding().userInput.getText().toString();
            String pass = binding().passInput.getText().toString();
            viewModel.loadUser(new AuthCredentials(user, pass));
        });

        /*binding().btnLogin.setOnClickListener(v -> NavHostFragment.findNavController(this)
                .navigate(LoginFragmentDirections.actionLoginToMain()));*/

        Glide.with(requireContext().getApplicationContext()).load(R.drawable.wall_image).into(binding().wallImage);
    }
}
