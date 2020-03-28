package tech.ankainn.edanapplication.form;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.Event;
import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseActivity;

public class CommonFormActivity extends BaseActivity {

    private SharedCommonFormViewModel viewModel;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_fragment_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SharedCommonFormViewModel.class);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    /*.add(R.id.container, new MapLocationFragment(), FragmentTag.MAP_LOCATION.toString())*/
                    .add(R.id.container, new GeneralInformationFragment(), FragmentTag.GEN_INFO.toString())
                    .commitAllowingStateLoss();
        }

        viewModel.getEvent().observe(this, this::handleEvent);
    }

    public void handleEvent(Enum event) {
        /*if(event == Event.OPEN_GEN_INFO_FRAGMENT) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new GeneralInformationFragment(), FragmentTag.GEN_INFO.toString())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }*/
    }
}
