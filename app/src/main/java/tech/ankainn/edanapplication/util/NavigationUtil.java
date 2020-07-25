package tech.ankainn.edanapplication.util;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class NavigationUtil {

    public static NavController getNavController(FragmentManager childFragmentManager, @IdRes int viewId) {
        Fragment fragment = childFragmentManager.findFragmentById(viewId);
        if (fragment instanceof NavHostFragment)
            return ((NavHostFragment) fragment).getNavController();
        else
            throw new RuntimeException("NavHostFragment not found in given view");
    }

    public static ViewModelStoreOwner getStoreOwner(FragmentManager childFragmentManager, @IdRes int viewId, @IdRes int graphId) {
        NavController navController = getNavController(childFragmentManager, viewId);
        return navController.getViewModelStoreOwner(graphId);
    }

    public static ViewModelProvider getViewModelProvider(FragmentManager childFragmentManager, @IdRes int viewId, @IdRes int graphId) {
        return new ViewModelProvider(getStoreOwner(childFragmentManager, viewId, graphId));
    }

    public static ViewModelProvider getViewModelProvider(NavController navController, @IdRes int navGraphId) {
        return new ViewModelProvider(navController.getViewModelStoreOwner(navGraphId));
    }
}
