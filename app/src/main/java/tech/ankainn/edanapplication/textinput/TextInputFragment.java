package tech.ankainn.edanapplication.textinput;

import android.os.Bundle;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import tech.ankainn.edanapplication.databinding.LayoutTextInputBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.OnAfterTextChanged;

public class TextInputFragment extends BottomSheetDialogFragment {

    private AutoClearedValue<LayoutTextInputBinding> binding;

    private TextInputViewModel viewModel;

    private boolean hidePassword;
    private CharSequence currentText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutTextInputBinding bindingTemp = LayoutTextInputBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(bindingTemp);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);

        viewModel = new ViewModelProvider(requireActivity()).get(TextInputViewModel.class);

        if(hidePassword) {
            binding.get().textInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.get().inputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        }

        if(currentText != null && currentText.length() > 0) {
            binding.get().textInput.setText(currentText);
        }

        binding.get().textInput.addTextChangedListener(new OnAfterTextChanged() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setText(s);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!requireActivity().isChangingConfigurations()) {
            viewModel.clearData();
        }
    }

    public void setHidePassword(boolean hide) {
        hidePassword = hide;
    }

    public void setCurrentText(CharSequence currentText) {
        this.currentText = currentText;
    }
}
