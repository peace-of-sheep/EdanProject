package tech.ankainn.edanapplication.ui.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import tech.ankainn.edanapplication.R;

public abstract class BaseActivity extends AppCompatActivity {

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int layout = getLayoutRes();
        if(layout != 0) {
            setContentView(layout);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enableNavigationBarLightMode();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void enableNavigationBarLightMode() {
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorDefaultBackground));
        boolean lightMode = getResources().getBoolean(R.bool.light_mode);
        if (lightMode) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
