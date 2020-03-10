package tech.ankainn.edanapplication.form;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.ViewDataBinding;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentGeneralInformationBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import timber.log.Timber;

public class GeneralInformationFragment extends BaseFragment {

    private AutoClearedValue<FragmentGeneralInformationBinding> binding;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_general_information;
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        this.binding = new AutoClearedValue<>((FragmentGeneralInformationBinding) binding);
        getViewLifecycleOwner().getLifecycle().addObserver(this.binding);
    }
}
