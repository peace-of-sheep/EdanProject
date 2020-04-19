package tech.ankainn.edanapplication;

import android.content.Intent;

import tech.ankainn.edanapplication.ui.auth.AuthActivity;
import tech.ankainn.edanapplication.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();

        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //TODO adding some kind of animation (?)
    }
}
