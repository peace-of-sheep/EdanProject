package tech.ankainn.edanapplication.ui.formOne;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentSwitchableBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class NeedsOthersFragment extends BindingFragment<FragmentSwitchableBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding().setTitle(getString(R.string.other_needs));
        binding().setOptionsVisibility(true);
        binding().setBottomVisibility(false);

        ViewModelProvider.Factory factory = InjectorUtil.provideFormOneViewModelFactory(requireContext());
        SwitchableViewModel viewModel = new ViewModelProvider(this, factory).get(SwitchableViewModel.class);

        String[] names = getTitles();
        SwitchableAdapter adapter = new SwitchableAdapter(names);
        binding().recyclerView.setAdapter(adapter);

        viewModel.getNeedsOthers().observe(getViewLifecycleOwner(),
                selectableData -> adapter.submitList(selectableData.list));

        binding().btnAccept.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
    }

    private static String[] getTitles() {
        return new String[]{"Carpas",
                "Camas",
                "Frazadas",
                "Alimentos Fríos",
                "Crudos",
                "Utensilios/Menaje",
                "Herramientas",
                "Equipos",
                "Otros",
                "Equipos de busqueda y Rescate",
                "Médicos / Brigadas de Salud Profesional",
                "Atención pro hospitalaria",
                "Evacuación de heridos",
                "Bomberos",
                "Equipos de comunicación",
                "Ingeniéros / Arquitectos",
                "Otros",
                "Maquinaria Pesada",
                "Cisterna"
        };
    }
}
