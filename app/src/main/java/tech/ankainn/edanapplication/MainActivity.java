package tech.ankainn.edanapplication;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.security.SecureRandom;

import tech.ankainn.edanapplication.databinding.ActivityFragmentContainerBinding;
import tech.ankainn.edanapplication.model.app.auth.UserData;
import tech.ankainn.edanapplication.model.app.formOne.FormOneData;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.repositories.Cache;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityFragmentContainerBinding binding =
                ActivityFragmentContainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fragmentContainer.postDelayed(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enableNavigationBarLightMode();
            }
        }, 200L);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (BuildConfig.DEBUG) {
            if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                Cache cache = Cache.getInstance();

                Timber.tag(Tagger.DUMPER).v("%s", cache.getUserData().getValue());
                FormOneData formOneData = cache.getFormOneData().getValue();
                Timber.tag(Tagger.DUMPER).d("%s", formOneData == null ? "no form one data" : new Gson().toJson(formOneData));
                FormTwoData formTwoData = cache.getFormTwoData().getValue();
                Timber.tag(Tagger.DUMPER).d("%s", formTwoData == null ? "no form two data" : new Gson().toJson(formTwoData));

                Timber.tag(Tagger.DUMPER).d("MainActivity.onKeyDown: %s", System.currentTimeMillis());
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
