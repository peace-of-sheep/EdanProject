package tech.ankainn.edanapplication.form;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseActivity;

public class CommonFormActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        final SharedCommonFormViewModel sharedViewModel = new ViewModelProvider(this).get(SharedCommonFormViewModel.class);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container,new LocationFragment())
                    .commit();
        }

        sharedViewModel.getFullscreen().observe(this, fullscreen -> {
            if(fullscreen) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });
    }
}
