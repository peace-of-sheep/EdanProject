package tech.ankainn.edanapplication.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import org.jetbrains.annotations.NotNull;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentGeneralInformationBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class GeneralInformationFragment extends BaseFragment {

    private AutoClearedValue<FragmentGeneralInformationBinding> binding;

    @NotNull
    @Override
    protected View getViewBinding(LayoutInflater inflater, ViewGroup container) {
        FragmentGeneralInformationBinding bindingTemp =
                DataBindingUtil.inflate(inflater, R.layout.fragment_general_information, container, false);
        binding = new AutoClearedValue<>(bindingTemp);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] COUNTRIES = new String[] {"Item 1", "Item 2", "Item 3", "Item 4"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.layout_dropdown_menu_item,
                        COUNTRIES);

        binding.get().textDangerGroup.setAdapter(adapter);
    }
}
