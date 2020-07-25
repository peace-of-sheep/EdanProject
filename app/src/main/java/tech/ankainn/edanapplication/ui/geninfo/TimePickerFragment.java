package tech.ankainn.edanapplication.ui.geninfo;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Locale;

public class TimePickerFragment extends AppCompatDialogFragment {

    private static boolean open = false;

    private final TimePickerDialog.OnTimeSetListener listener = (v, h, m) -> {
        String hour = String.format(Locale.getDefault(), "%02d:%02d", h, m);
        Fragment fragment = getTargetFragment();
        if(fragment instanceof HourListener) {
            ((HourListener) fragment).setHour(hour);
        }
    };

    public static void showDialog(Fragment targetFragment) {
        if(!open) {
            open = true;
            DialogFragment fragment = new TimePickerFragment();
            fragment.setTargetFragment(targetFragment, 0);
            fragment.show(targetFragment.getParentFragmentManager(), "time");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(requireContext(), listener, hour, minute, true);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        open = false;
    }

    public interface HourListener {
        void setHour(String hour);
    }
}
