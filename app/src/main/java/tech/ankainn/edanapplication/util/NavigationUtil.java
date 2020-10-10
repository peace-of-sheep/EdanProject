package tech.ankainn.edanapplication.util;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.view.NavigatorView;

public class NavigationUtil {

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

    public static NavController getChildNavController(FragmentManager childFragmentManager, @IdRes int viewId) {
        Fragment fragment = childFragmentManager.findFragmentById(viewId);
        if (fragment instanceof NavHostFragment)
            return ((NavHostFragment) fragment).getNavController();
        else
            throw new RuntimeException("NavHostFragment not found in given view");
    }
}
