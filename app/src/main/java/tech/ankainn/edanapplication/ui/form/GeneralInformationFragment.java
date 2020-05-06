package tech.ankainn.edanapplication.ui.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.ui.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentGeneralInformationBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class GeneralInformationFragment extends BaseFragment {

    private AutoClearedValue<FragmentGeneralInformationBinding> binding;

    @NotNull
    @Override
    protected View makeView(LayoutInflater inflater, ViewGroup container) {
        FragmentGeneralInformationBinding bindingTemp =
                FragmentGeneralInformationBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(bindingTemp);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);

        String[] COUNTRIES = new String[] {"Item 1", "Item 2", "Item 3", "Item 4"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        requireContext(),
                        R.layout.layout_dropdown_menu_item,
                        COUNTRIES);

        binding.get().textDangerGroup.setAdapter(adapter);
        binding.get().textDangerType.setAdapter(adapter);

        binding.get().btnNext.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new NewFormsFragment())
                        .commit());
    }
}
