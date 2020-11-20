package tech.ankainn.edanapplication.ui.geninfo;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.danger.DangerEntity;
import tech.ankainn.edanapplication.databinding.FragmentHeaderBinding;
import tech.ankainn.edanapplication.model.app.ubigeo.UbigeoLocation;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class HeaderFragment extends BindingFragment<FragmentHeaderBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideGenInfViewModelFactory(requireContext());
        HeaderViewModel viewModel = new ViewModelProvider(this, factory).get(HeaderViewModel.class);

        viewModel.getHeaderData().observe(getViewLifecycleOwner(),
                headerData -> binding().setHeaderData(headerData));

        viewModel.getNameDangerGroup().observe(getViewLifecycleOwner(), name -> binding().textDangerGroup.setText(name));
        viewModel.getListDangers().observe(getViewLifecycleOwner(), dangers -> {
            binding().setListDangers(dangers);
        });
        binding().textDangerType.setOnItemClickListener((p, v, pos, id) -> {
            DangerEntity danger = (DangerEntity) binding().textDangerType.getAdapter().getItem(pos);
            viewModel.onDanger(danger);
        });

        viewModel.getListDept().observe(getViewLifecycleOwner(), list -> binding().setListDept(list));
        viewModel.getListProv().observe(getViewLifecycleOwner(), list -> binding().setListProv(list));
        viewModel.getListDist().observe(getViewLifecycleOwner(), list -> binding().setListDist(list));
        viewModel.getListLocal().observe(getViewLifecycleOwner(), list -> binding().setListLocal(list));

        binding().textDepartment.setOnItemClickListener((p, v, pos, id) -> {
            binding().textProvince.setText(null);
            binding().textDistrict.setText(null);
            binding().textLocality.setText(null);

            UbigeoLocation u = (UbigeoLocation) binding().textDepartment.getAdapter().getItem(pos);
            viewModel.onDepartment(u);
        });
        binding().textProvince.setOnItemClickListener((p, v, pos, id) -> {
            binding().textDistrict.setText(null);
            binding().textLocality.setText(null);

            UbigeoLocation u = (UbigeoLocation) binding().textProvince.getAdapter().getItem(pos);
            viewModel.onProvince(u);
        });
        binding().textDistrict.setOnItemClickListener((p, v, pos, id) -> {
            binding().textLocality.setText(null);

            UbigeoLocation u = (UbigeoLocation) binding().textDistrict.getAdapter().getItem(pos);
            viewModel.onDistrict(u);
        });
        binding().textLocality.setOnItemClickListener((p, v, pos, id) -> {
            UbigeoLocation u = (UbigeoLocation) binding().textLocality.getAdapter().getItem(pos);
            viewModel.onLocality(u);
        });
    }
}
