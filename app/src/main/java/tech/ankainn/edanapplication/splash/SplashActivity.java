package tech.ankainn.edanapplication.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import tech.ankainn.edanapplication.BuildConfig;
import tech.ankainn.edanapplication.auth.AuthActivity;
import tech.ankainn.edanapplication.base.BaseActivity;
import tech.ankainn.edanapplication.form.CommonFormActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO debug
        if(BuildConfig.DEBUG) {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
            return;
        }


        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //TODO adding some kind of animation (?)
    }
}
