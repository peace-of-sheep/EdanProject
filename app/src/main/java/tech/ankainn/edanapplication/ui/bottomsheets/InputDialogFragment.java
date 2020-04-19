package tech.ankainn.edanapplication.ui.bottomsheets;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.TooltipCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.DialogInputBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.OnAfterTextChanged;

/**
 * Fragment for input text, use {@link Util} class to create a instance
 */
public class InputDialogFragment extends BottomSheetDialogFragment {

    /**
     * This dialog is opened
     */
    public static final int OPENED = 1;

    /**
     * This dialog is closed
     */
    public static final int CLOSED = 2;

    @IntDef({OPENED, CLOSED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DialogState {}

    private static final String HIDE_KEY = "hide";
    private static final String ORIGINAL_TEXT_KEY = "original";

    private AutoClearedValue<DialogInputBinding> binding;

    private CharSequence originalText;
    private boolean hidePassword;

    private Listener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            hidePassword = args.getBoolean(HIDE_KEY);
            originalText = args.getCharSequence(ORIGINAL_TEXT_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DialogInputBinding bindingTemp = DialogInputBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(bindingTemp);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Fragment targetFragment = getTargetFragment();
        if(targetFragment instanceof Listener) {
            listener = (Listener) targetFragment;
        } else {
            dismiss();
            return;
        }

        listener.onState(OPENED);

        binding.get().setBtnListener(v -> dismiss());

        TooltipCompat.setTooltipText(binding.get().btnDone, getString(R.string.done));

        if(hidePassword) {
            binding.get().textInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.get().inputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        }

        if (notEmptyText(originalText) && emptyTextView(binding.get().textInput)) {
            binding.get().textInput.setText(originalText);
        }

        binding.get().textInput.addTextChangedListener(new OnAfterTextChanged() {
            @Override
            public void afterTextChanged(Editable s) {
                listener.onTextChanged(s);
            }
        });
        
        binding.get().textInput.setOnFocusChangeListener((v, focus) -> {
            if (focus) {
                Window w = requireDialog().getWindow();
                if (w != null)
                    w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                binding.get().textInput.setOnFocusChangeListener(null);
            }
        });


        /*getContext()*/
        /*InputMethodManager imm = (InputMethodManager) w.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);*/

        binding.get().textInput.requestFocus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!requireActivity().isChangingConfigurations()) {
            if(listener != null) listener.onState(CLOSED);
        }
        listener = null;
    }

    private boolean notEmptyText(CharSequence text) {
        return text != null && text.length() > 0;
    }

    private boolean emptyTextView(TextView view) {
        return view.getText() == null || view.getText().length() == 0;
    }

    private static InputDialogFragment create(Fragment targetFragment, CharSequence originalText,
                                              boolean hideText) {
        Bundle args = new Bundle();
        args.putBoolean(HIDE_KEY, hideText);
        args.putCharSequence(ORIGINAL_TEXT_KEY, originalText);

        InputDialogFragment fragment = new InputDialogFragment();
        fragment.setArguments(args);
        fragment.setTargetFragment(targetFragment, 0);
        return fragment;
    }

    /**
     * Fragments should implement this interface in order to listen input text changes.
     */
    public interface Listener {

        /**
         * Capture target view listener.
         * @return target {@link TextView}
         */
        @Nullable
        TextView getViewListener();

        default void onTextChanged(CharSequence s) {
            TextView view = getViewListener();
            if(view != null)
                view.setText(s);
        }

        void onState(@DialogState int state);
    }

    /**
     * Util class to open InputDialogFragment instance
     */
    public static class Util {

        private static final String TAG = "input_tag";

        private static boolean opened;

        public static boolean showInputDialog(FragmentManager fm, TextView targetView,
                                           Fragment targetFragment) {
            if(opened) return false;
            opened = true;

            boolean hideText = targetView.getTransformationMethod() instanceof PasswordTransformationMethod;
            CharSequence currentText = targetView.getText();

            InputDialogFragment fragment = InputDialogFragment.create(targetFragment, currentText, hideText);
            fragment.show(fm, TAG);
            fm.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                    if(f instanceof InputDialogFragment && !f.requireActivity().isChangingConfigurations()) {
                        opened = false;
                        fm.unregisterFragmentLifecycleCallbacks(this);
                    }
                }
            }, false);
            return true;
        }

        public static void dismissInputDialog(FragmentManager fm) {
            //TODO refactor this code
            Fragment fragment = fm.findFragmentByTag(TAG);
            if(fragment instanceof InputDialogFragment)
                ((InputDialogFragment) fragment).dismiss();
        }
    }
}
