package tech.ankainn.edanapplication.auth;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.Event;
import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseActivity;
import timber.log.Timber;

public class AuthActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_fragment_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedAuthViewModel viewModel =
                new ViewModelProvider(this).get(SharedAuthViewModel.class);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new WallFragment())
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }

        viewModel.getEvent().observe(this, this::handleEvent);

        Timber.tag("TextFlow").d("onCreate: activity %s", this);
    }

    private void handleEvent(Event event) {
        /*if(event == Event.NAV_TO_COMMON_ACTIVITY) {
            startActivity(new Intent(this, CommonFormActivity.class));
            finish();
        }*/
    }
}
