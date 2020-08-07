package tech.ankainn.edanapplication.ui.formTwoA;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

import tech.ankainn.edanapplication.R;

public class BackDialogFragment extends AppCompatDialogFragment {

    private DialogInterface.OnClickListener positiveListener = (d, w) -> {
        Navigation.findNavController(requireActivity(), R.id.fragment_container).popBackStack();
        dismiss();
    };

    private DialogInterface.OnClickListener negativeListener = (d, w) -> {
        dismiss();
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // TODO redo msg
        return new AlertDialog.Builder(requireContext())
                .setMessage("¿Desea salir del formulario actual?")
                .setPositiveButton(R.string.accept, positiveListener)
                .setNegativeButton(R.string.cancel, negativeListener)
                .create();
    }
}
