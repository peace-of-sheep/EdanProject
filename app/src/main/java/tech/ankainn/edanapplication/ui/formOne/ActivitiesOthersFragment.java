package tech.ankainn.edanapplication.ui.formOne;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentSwitchableBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class ActivitiesOthersFragment extends BindingFragment<FragmentSwitchableBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding().setTitle(getString(R.string.other_activities));
        binding().setBottomVisibility(false);
        binding().setOptionsVisibility(true);

        ViewModelProvider.Factory factory = InjectorUtil.provideFormOneViewModelFactory(requireContext());
        SwitchableViewModel viewModel = new ViewModelProvider(this, factory).get(SwitchableViewModel.class);

        String[] names = getTitles();
        SwitchableAdapter adapter = new SwitchableAdapter(names);
        binding().recyclerView.setAdapter(adapter);

        viewModel.getActivitiesOthers().observe(getViewLifecycleOwner(),
                selectableData -> adapter.submitList(selectableData.list));

        binding().btnAccept.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
    }

    private static String[] getTitles() {
        return new String[]{"Rescate de personas atrapadas",
                "Busqueda de desaparecidos",
                "Atención pre-hospitalaria",
                "Evacuación de heridos",
                "Evacuación de damnificados y afectados",
                "Evacuación de población en riesgo",
                "Atención de lesionados (heridos)",
                "Asistencia con techo temporal",
                "Asistencia con ropa de abrigo",
                "Asistencia alimentaria",
                "Provición de agua segura",
                "Rehabilitación de servicios",
                "Evaluadores EDAN",
                "Rehabilitación de vías",
                "Equipos de comunicación",
                "Remoción de escombros",
                "Instalación de albergues",
                "Instalación de letrinas",
                "Disposición de desechos solidos (basura)",
                "Hospital de campaña / Equipos medicos de emergencia - EMT",
                "Gestión de restos humanos",
                "Seguridad",
                "Evaluación de riesgo",
                "Utensilios - Menaje",
                "Herramientas",
                "Maquinaria pesada"
        };
    }
}
