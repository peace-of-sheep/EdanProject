package tech.ankainn.edanapplication.global;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.Locale;

/**
 * DialogFragment for picking date/time.
 */
public class PickerFragment extends AppCompatDialogFragment {

    @IntDef({MODE_DATE, MODE_TIME})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ModePicker {}

    /**
     * Mode for date picker.
     */
    public static final int MODE_DATE = 0;

    /**
     * Mode for time picker.
     */
    public static final int MODE_TIME = 1;

    public static final String MODE_KEY = "mode_key";
    public static final String PICKER_TAG = "picker_tag";

    /**
     * Flag for checking the availability
     */
    private static boolean flag_open = false;

    private int mode;

    /**
     * Listener for date picker fragment.
     */
    private final DatePickerDialog.OnDateSetListener dateListener = (v, y, m ,d) -> {
        String date = String.format(Locale.getDefault(), "%02d/%02d/%04d", d, m+1, y);
        Picker.getInstance().setValue("date", date);
    };

    /**
     * Listener for hour picker fragment.
     */
    private final TimePickerDialog.OnTimeSetListener timeListener = (v, h, m) -> {
        String hour = String.format(Locale.getDefault(), "%02d:%02d", h, m);
        Picker.getInstance().setValue("hour", hour);
    };

    /**
     *
     * @param mode
     * @param target
     */
    public static void showPicker(@ModePicker int mode, Fragment target) {
        if(!flag_open) {
            flag_open = true;
            DialogFragment fragment = new PickerFragment();
            Bundle args = new Bundle();
            args.putInt(MODE_KEY, mode);
            fragment.setArguments(args);
            fragment.show(target.getParentFragmentManager(), PICKER_TAG);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Bundle args = getArguments();
        if (args != null) {
            mode = args.getInt(MODE_KEY);
        }

        final Context context = requireContext();
        final Calendar calendar = Calendar.getInstance();

        switch (mode) {
            case MODE_DATE:
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(context, dateListener, year, month, day);

            case MODE_TIME:
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                return new TimePickerDialog(context, timeListener, hour, minute, true);

            default:
                throw new RuntimeException("Invalid mode");
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        flag_open = false;
    }
}
