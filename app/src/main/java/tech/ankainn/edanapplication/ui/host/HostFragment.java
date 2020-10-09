package tech.ankainn.edanapplication.ui.host;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentHostBinding;
import tech.ankainn.edanapplication.global.BottomOptionsFragment;
import tech.ankainn.edanapplication.global.Options;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.OwnerFragment;

import static tech.ankainn.edanapplication.util.NavigationUtil.getChildNavController;

public class HostFragment extends BindingFragment<FragmentHostBinding> implements OwnerFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavController childNavController = getChildNavController(getChildFragmentManager(), R.id.host_fragment_container);
        NavigationUI.setupWithNavController(binding().bnv, childNavController);

        /*binding().btnAdd.setOnClickListener(v ->
                new BottomOptionsFragment.Builder("select")
                        .setTitle(R.string.select_form)
                        .addOption(R.string.form_one, R.drawable.ic_folder_24dp)
                        .addOption(R.string.form_two, R.drawable.ic_folder_24dp)
                        .build(getParentFragmentManager()));*/

        binding().btnAdd.setOnClickListener(v ->
                Navigation.findNavController(requireActivity(), R.id.fragment_container)
                        .navigate(HostFragmentDirections.actionHostToFormTwo()));

        Options.getInstance().observe(getViewLifecycleOwner(), (emitter, option) -> {
            if ("select".equals(emitter)) {
                if (option == 0) {
                    Navigation.findNavController(requireActivity(), R.id.fragment_container)
                            .navigate(HostFragmentDirections.actionHostToFormOne());
                } else if (option == 1) {
                    Navigation.findNavController(requireActivity(), R.id.fragment_container)
                                .navigate(HostFragmentDirections.actionHostToFormTwo());
                }
            }
        });
    }

    @Override
    public int getDestinationId() {
        return R.id.host_fragment;
    }
}
