package tech.ankainn.edanapplication;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        //TODO adding some kind of animation (?)
    }
}
