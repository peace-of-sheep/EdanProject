package tech.ankainn.edanapplication.ui.form;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.ui.base.BaseActivity;
import tech.ankainn.edanapplication.global.FragmentTag;

public class CommonFormActivity extends BaseActivity {

    private CommonFormViewModel viewModel;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_fragment_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CommonFormViewModel.class);

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
