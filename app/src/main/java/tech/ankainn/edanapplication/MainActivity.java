package tech.ankainn.edanapplication;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import tech.ankainn.edanapplication.databinding.ActivityFragmentContainerBinding;
import tech.ankainn.edanapplication.ui.auth.LoginFragment;
import tech.ankainn.edanapplication.ui.auth.WallFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityFragmentContainerBinding binding =
                ActivityFragmentContainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            enableNavigationBarLightMode();
        }

        int containerId = binding.container.getId();

        if(savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(containerId, new WallFragment())
                    .add(containerId, new LoginFragment())
                    .commit();
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
}
