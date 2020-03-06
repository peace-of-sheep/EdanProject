package tech.ankainn.edanapplication.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseActivity;
import tech.ankainn.edanapplication.form.CommonFormActivity;

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        final SharedAuthViewModel viewModel =
                new ViewModelProvider(this).get(SharedAuthViewModel.class);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new WallFragment())
                    .add(R.id.container, LoginFragment.newInstance())
                    .commit();
        }

        viewModel.getNavigator().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                viewModel.getNavigator().removeObserver(this);
                startActivity(new Intent(AuthActivity.this, CommonFormActivity.class));
                finish();
            }
        });
    }
}
