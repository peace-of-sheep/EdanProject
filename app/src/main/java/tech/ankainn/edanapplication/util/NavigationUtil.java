package tech.ankainn.edanapplication.util;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class NavigationUtil {

    public static NavController getChildNavController(FragmentManager childFragmentManager, @IdRes int viewId) {
        Fragment fragment = childFragmentManager.findFragmentById(viewId);
        if (fragment instanceof NavHostFragment)
            return ((NavHostFragment) fragment).getNavController();
        else
            throw new RuntimeException("NavHostFragment not found in given view");
    }
}
