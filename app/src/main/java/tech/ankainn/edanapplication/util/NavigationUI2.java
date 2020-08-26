package tech.ankainn.edanapplication.util;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.view.NavigatorView;

public class NavigationUI2 {

    public static void setupWithNavController(NavigatorView view, NavController navController) {
        view.setOnBackListener(destinationId -> onNavigation(destinationId, navController));
        view.setOnForwardListener(destinationId -> onNavigation(destinationId, navController));
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
