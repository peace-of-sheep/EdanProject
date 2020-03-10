package tech.ankainn.edanapplication.form;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentLocationBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import timber.log.Timber;

public class LocationFragment extends BaseFragment {

    private AutoClearedValue<FragmentLocationBinding> binding;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_location;
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        this.binding = new AutoClearedValue<>((FragmentLocationBinding) binding);
        getViewLifecycleOwner().getLifecycle().addObserver(this.binding);
    }

    @Override
    protected boolean isClickable() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedCommonFormViewModel sharedViewModel =
                new ViewModelProvider(requireActivity()).get(SharedCommonFormViewModel.class);

        sharedViewModel.getLocation().observe(getViewLifecycleOwner(),
                location -> binding.get().setLatLng(location));

        binding.get().btnNext.setOnClickListener(v -> {
            navigateToGenInfo();
        });

        Timber.tag("GeneralInformationFrag").d("onViewCreated: %s", requireActivity().getWindow().getDecorView().getSystemUiVisibility());
    }

    private void navigateToGenInfo() {
        /*NavigatorController.navigateTo(requireActivity(), new GeneralInformationFragment());*/
        /*getParentFragmentManager()
                .beginTransaction()
                .hide(this)
                .hide(getParentFragmentManager().findFragmentByTag(FragmentTag.MAP_LOCATION.toString()))
                .add(R.id.container, new GeneralInformationFragment(), FragmentTag.GEN_INFO.toString())
                .commit();*/

        ((CommonFormActivity) requireActivity()).navigateToGenInfo();
    }
}
