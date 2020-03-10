package tech.ankainn.edanapplication.form;

import android.os.Bundle;

import androidx.annotation.Nullable;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseActivity;

public class CommonFormActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_fragment_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new MapLocationFragment(), FragmentTag.MAP_LOCATION.toString())
                    .add(R.id.container, new LocationFragment(), FragmentTag.LOCATION.toString())
                    .commit();
        }
    }

    public void navigateToGenInfo() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new GeneralInformationFragment(), FragmentTag.GEN_INFO.toString())
                .addToBackStack(null)
                .commit();
    }
}
