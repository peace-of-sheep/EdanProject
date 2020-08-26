package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import com.google.gson.Gson;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentHouseholdBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.Tagger;
import tech.ankainn.edanapplication.viewmodel.FormTwoViewModelFactory;
import timber.log.Timber;

public class HouseholdFragment extends BindingFragment<FragmentHouseholdBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelStoreOwner owner = ScopeNavHostFragment.getOwner(this);
        FormTwoViewModelFactory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        FormTwoViewModel viewModel = new ViewModelProvider(owner, factory).get(FormTwoViewModel.class);

        viewModel.getHouseholdData().observe(getViewLifecycleOwner(),
                householdData -> binding().setHousehold(householdData));
    }
}
