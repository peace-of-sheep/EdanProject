package tech.ankainn.edanapplication.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import tech.ankainn.edanapplication.auth.AuthActivity;
import tech.ankainn.edanapplication.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, AuthActivity.class));
        finish();


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }
}
