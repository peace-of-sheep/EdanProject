package tech.ankainn.edanapplication.auth;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

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

        boolean lightMode = getResources().getBoolean(R.bool.light_mode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && lightMode) {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        } else {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow()
                .getDecorView()
                .setSystemUiVisibility(View.VISIBLE);
    }
}
