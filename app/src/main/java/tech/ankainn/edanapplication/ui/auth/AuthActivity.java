package tech.ankainn.edanapplication.ui.auth;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.global.Event;
import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.ui.base.BaseActivity;
import tech.ankainn.edanapplication.ui.formOne.SwitchableFragment;

public class AuthActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_fragment_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final AuthViewModel viewModel =
                new ViewModelProvider(this).get(AuthViewModel.class);

        if(savedInstanceState == null) {

            final Fragment fragment = new LoginFragment();

            /*final Fragment fragment = SwitchableFragment
                    .create(R.string.title_damage_one,
                            R.array.damage_one,
                            false,
                            null,
                            true,
                            true);*/

            getSupportFragmentManager()
                    .beginTransaction()
                    //.add(R.id.container, new WallFragment())
                    //.add(R.id.container, new LoginFragment())
                    .add(R.id.container, fragment)
                    .commit();
        }

        viewModel.getEvent().observe(this, this::handleEvent);
    }

    private void handleEvent(Event event) {
        /*if(event == Event.NAV_TO_COMMON_ACTIVITY) {
            startActivity(new Intent(this, CommonFormActivity.class));
            finish();
        }*/
    }
}
