package tech.ankainn.edanapplication.ui.geninfo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment {

    private static boolean open = false;

    private DatePickerDialog.OnDateSetListener listener = (v, y, m, d) -> {
        String date = String.format(Locale.getDefault(), "%02d/%02d/%04d", d, m, y);

        Fragment fragment = getTargetFragment();
        if(fragment instanceof DateListener)
            ((DateListener) fragment).setDate(date);
    };

    public static void showDialog(Fragment targetFragment) {
        if(!open) {
            open = true;
            DialogFragment fragment = new DatePickerFragment();
            fragment.setTargetFragment(targetFragment, 0);
            fragment.show(targetFragment.getParentFragmentManager(), "date");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(requireContext(), listener, year, month, day);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        open = false;
    }

    public interface DateListener {
        void setDate(String date);
    }
}
