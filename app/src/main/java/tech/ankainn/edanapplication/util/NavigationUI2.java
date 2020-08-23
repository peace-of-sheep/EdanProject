package tech.ankainn.edanapplication.util;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;

import tech.ankainn.edanapplication.R;

public class NavigationUI2 {

    public static void setupWithNavController(View next, View previous, int[] destinations, NavController navController) {
        next.setOnClickListener(v -> {
            int position = 0;
            for (int i = 0; i < destinations.length; i++) {
                if(destinations[i] == navController.getCurrentDestination().getId()) {
                    position = i;
                    break;
                }
            }

            if(++position == destinations.length) {
                position = 0;
            }

            onNavigation(destinations[position], navController);
        });

        previous.setOnClickListener(v -> {
            int position = 0;
            for (int i = 0; i < destinations.length; i++) {
                if(destinations[i] == navController.getCurrentDestination().getId()) {
                    position = i;
                    break;
                }
            }

            if(--position < 0) {
                position = destinations.length - 1;
            }

            onNavigation(destinations[position], navController);
        });
    }

    public static void onNavigation(int destinationId, NavController navController) {
        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.nav_default_enter_anim)
                .setExitAnim(R.anim.nav_default_exit_anim)
                .setPopUpTo(navController.getCurrentDestination().getId(), true)
                .build();

        navController.navigate(destinationId, null, options);
    }
}
