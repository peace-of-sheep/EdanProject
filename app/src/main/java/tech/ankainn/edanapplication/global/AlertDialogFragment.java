package tech.ankainn.edanapplication.global;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import tech.ankainn.edanapplication.R;

public class AlertDialogFragment extends AppCompatDialogFragment {

    private static String KEY_EMITTER = "key_emitter";
    private static String KEY_MSG = "key_msg";

    private static boolean open = false;

    private String emitter;

    @StringRes
    private int msg;

    private DialogInterface.OnClickListener positiveListener = (d, w) -> {
        Options.getInstance().setOption(emitter, 0);
        dismiss();
    };

    private DialogInterface.OnClickListener negativeListener = (d, w) -> {
        Options.getInstance().setOption(emitter, 1);
        dismiss();
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        if (args != null) {
            emitter = args.getString(KEY_EMITTER);
            msg = args.getInt(KEY_MSG);
        }

        return new MaterialAlertDialogBuilder(requireContext())
                .setMessage(msg)
                .setPositiveButton(R.string.accept, positiveListener)
                .setNegativeButton(R.string.cancel, negativeListener)
                .create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        open = false;
    }

    public static class Builder {

        private String emitter;

        @StringRes
        private int msg;

        public Builder(String emitter) {
            this.emitter = emitter;
        }

        public Builder setMessage(@StringRes int res) {
            msg = res;
            return this;
        }

        public void build(FragmentManager fm) {
            if(!open) {
                open = true;

                Bundle args = new Bundle();
                args.putString(KEY_EMITTER, emitter);
                args.putInt(KEY_MSG, msg);

                DialogFragment fragment = new AlertDialogFragment();
                fragment.setArguments(args);
                fragment.show(fm, "alert");
            }
        }
    }
}
