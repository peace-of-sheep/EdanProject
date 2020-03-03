package tech.ankainn.edanapplication.auth;

import android.os.Bundle;

import androidx.annotation.Nullable;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseActivity;
import tech.ankainn.edanapplication.util.WallFragment;

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new WallFragment())
                    .add(R.id.container, LoginFragment.newInstance())
                    .commit();
        }
    }
}
