package tech.ankainn.edanapplication.ui.bottomsheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.TooltipCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.DialogInputBinding;
import tech.ankainn.edanapplication.ui.base.ViewFinder;
import tech.ankainn.edanapplication.util.AutoClearedValue;

/**
 * Fragment for input text
 */
public class InputDialogFragment extends BottomSheetDialogFragment {

    private static final String TAG = "input_tag";

    private static final String VIEW_KEY = "view";

    private AutoClearedValue<DialogInputBinding> binding;

    private InputDialogViewModel viewModel;

    private int viewId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            viewId = args.getInt(VIEW_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DialogInputBinding bindingTemp = DialogInputBinding.inflate(inflater, container, false);
        bindingTemp.setLifecycleOwner(getViewLifecycleOwner());
        binding = new AutoClearedValue<>(bindingTemp);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Fragment fragment = getTargetFragment();
        if(!(fragment instanceof ViewFinder))
            throw new RuntimeException("Target fragment should implement ViewFinder");

        TextView view = ((ViewFinder) fragment).findViewById(viewId);

        if(view == null) {
            dismiss();
            return;
        }

        viewModel = new ViewModelProvider(this).get(InputDialogViewModel.class);

        viewModel.setState(true);

        viewModel.setData(view.getText());
        binding.get().textInput.setText(view.getText());

        binding.get().setBtnListener(v -> dismiss());
        binding.get().setViewModel(viewModel);
        binding.get().executePendingBindings();

        TooltipCompat.setTooltipText(binding.get().btnDone, getString(R.string.done));

        /*if(hidePassword) {
            binding.get().textInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.get().inputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        }*/

        viewModel.getData().observe(getViewLifecycleOwner(), view::setText);

        //*********************************************************
        binding.get().textInput.setOnFocusChangeListener((v, focus) -> {
            if (focus) {
                Window w = requireDialog().getWindow();
                if (w != null)
                    w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                binding.get().textInput.setOnFocusChangeListener(null);
            }
        });
        binding.get().textInput.requestFocus();

        /*getContext()*/
        /*InputMethodManager imm = (InputMethodManager) w.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);*/
    }

    @Override
    public void onDestroyView() {
        if(!requireActivity().isChangingConfigurations()) {
            viewModel.setState(false);
        }
        super.onDestroyView();
    }

    public static void create(FragmentManager fm, Fragment targetFragment, @IdRes int viewId) {
        Bundle args = new Bundle();
        args.putInt(VIEW_KEY, viewId);

        InputDialogFragment fragment = new InputDialogFragment();
        fragment.setArguments(args);
        fragment.setTargetFragment(targetFragment, 0);
        fragment.show(fm, TAG);
    }

    public static void dismissInputDialog(FragmentManager fm) {
        //TODO refactor this code
        Fragment fragment = fm.findFragmentByTag(TAG);
        if(fragment instanceof InputDialogFragment)
            ((InputDialogFragment) fragment).dismiss();
    }
}
