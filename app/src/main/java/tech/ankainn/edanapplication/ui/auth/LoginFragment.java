package tech.ankainn.edanapplication.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentLoginBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import timber.log.Timber;

public class LoginFragment extends BindingFragment<FragmentLoginBinding> {

    @Override
    protected FragmentLoginBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(requireContext().getApplicationContext()).load(R.drawable.wall_image).into(binding().wallImage);

        binding().btnLogin.setOnClickListener(v -> Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(LoginFragmentDirections.actionLoginToMain()));
    }
}
