package tech.ankainn.edanapplication.ui.formTwoA;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import tech.ankainn.edanapplication.model.api.ReniecData;

public class Utilities {

    public static void setPersonData(ReniecData personReniecData, TextView nameView,
                                     TextView ageView, TextView genderView, TextView birthdateView,
                                     String[] arrayGender) {
        if (personReniecData != null && personReniecData.apellidos != null && personReniecData.nombres != null) {
            String[] rawSurname = personReniecData.apellidos.split(",");
            String surname = rawSurname[0]+rawSurname[1];

            String resGender = Objects.equals(personReniecData.tipoSexo, "M") ? arrayGender[0] : arrayGender[1];

            nameView.setText(personReniecData.nombres);
            ageView.setText(personReniecData.edad);
            genderView.setText(resGender);
            birthdateView.setText(personReniecData.birthdate);
        } else {
            Context context = nameView.getContext();
            Toast.makeText(context, "Dni no valido", Toast.LENGTH_SHORT).show();
        }
    }

    public static void searchIfCorrectType(MembersViewModel viewModel, AutoCompleteTextView idTypeView,
                                           TextView idNumberView, String resDni) {
        Context context = idTypeView.getContext();

        String idType = idTypeView.getText().toString();
        if (Objects.equals(idType, resDni)) {
            String dni = idNumberView.getText().toString();
            viewModel.searchPersonByDni(dni);
        } else {
            Toast.makeText(context, "Solo usar dni", Toast.LENGTH_SHORT).show();
        }
    }
}
