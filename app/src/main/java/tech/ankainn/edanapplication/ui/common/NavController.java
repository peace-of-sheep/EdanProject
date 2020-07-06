package tech.ankainn.edanapplication.ui.common;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.ui.form.MapLocationFragment;
import tech.ankainn.edanapplication.ui.form.NewFormsFragment;
import tech.ankainn.edanapplication.ui.formOne.SwitchableFragment;
import tech.ankainn.edanapplication.ui.formTwo.HouseholdInfoFragment;

public interface NavController {

    int containerId = R.id.fragment_container;

    static void openSwitchable(FragmentActivity activity, int n) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment fragment = new NewFormsFragment();
        if(n == 0) {
            fragment = SwitchableFragment.create(R.string.title_damage_one, R.array.damage_one, false, 0, true, true, 1);
        } else if(n == 1) {
            fragment = SwitchableFragment.create(R.string.title_damage_two, R.array.damage_two, false, 0, false, true, 2);
        } else if(n == 2) {
            fragment = SwitchableFragment.create(R.string.title_damage_three, R.array.damage_three, false, 0, false, true, 3);
        } else if(n == 3) {
            fragment = SwitchableFragment.create(R.string.title_activities, R.array.activities, true, R.string.other_activities, false, true, 4);
        } else if(n == 4) {
            fragment = SwitchableFragment.create(R.string.title_needs, R.array.needs, true, R.string.other_needs, false, true, -1);
        }
        ft.replace(containerId, fragment).commit();
    }

    static void openNewForms(FragmentActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, new NewFormsFragment())
                .commit();
    }

    static void openHouseholdInfo(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction().replace(containerId, new HouseholdInfoFragment()).commit();
    }
}
