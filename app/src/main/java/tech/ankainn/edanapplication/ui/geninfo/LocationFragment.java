package tech.ankainn.edanapplication.ui.geninfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentLocationBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;

public class LocationFragment extends BindingFragment<FragmentLocationBinding> {

    private MapViewModel viewModel;

    @Override
    protected FragmentLocationBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLocationBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);

        int form = LocationFragmentArgs.fromBundle(requireArguments()).getForm();
        int graphId = form == 1 ? R.id.form_one_host_graph : R.id.form_two_host_graph;
        ViewModelProvider viewModelProvider = new ViewModelProvider(navController.getViewModelStoreOwner(graphId));

        viewModel = viewModelProvider.get(MapViewModel.class);

        viewModel.getMapLocationData().observe(getViewLifecycleOwner(),
                mapLocationData -> binding().setMapLocation(mapLocationData));
    }
}
