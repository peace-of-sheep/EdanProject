package tech.ankainn.edanapplication.ui.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tech.ankainn.edanapplication.ui.base.BaseFragment;
import tech.ankainn.edanapplication.databinding.FragmentNewFormsBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class NewFormsFragment extends BaseFragment {

    private AutoClearedValue<FragmentNewFormsBinding> binding;

    @NonNull
    @Override
    protected View makeView(LayoutInflater inflater, ViewGroup container) {
        FragmentNewFormsBinding binding = FragmentNewFormsBinding.inflate(inflater, container, false);
        this.binding = new AutoClearedValue<>(binding);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.get().frameAdd.setOnClickListener(v -> {});
    }
}
