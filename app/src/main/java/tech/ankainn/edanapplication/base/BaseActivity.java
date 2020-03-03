package tech.ankainn.edanapplication.base;

import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import tech.ankainn.edanapplication.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        allowFullscreen();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    protected void allowFullscreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean lightMode = getResources().getBoolean(R.bool.light_mode);
            if(lightMode) {
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
    }
}
