package tech.ankainn.edanapplication.ui.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.fragment.NavHostFragment;

public class ScopeNavHostFragment extends NavHostFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(requireParentFragment() instanceof OwnerFragment) {
            super.onCreate(savedInstanceState);
        } else {
            throw new RuntimeException("ScopeNavHostFragment parent fragment must implement OwnerFragment interface");
        }
    }

    /**
     * Obtain viewModelProvider associate to parent fragment scope if any
     * @param fragment
     * @return
     */
    public static ViewModelStoreOwner getOwner(Fragment fragment) {
        Fragment parentFragment = fragment.requireParentFragment();
        if (parentFragment instanceof ScopeNavHostFragment) {
            Fragment scopeFragment = parentFragment.requireParentFragment();
            OwnerFragment owner = (OwnerFragment) scopeFragment;
            return findNavController(scopeFragment).getBackStackEntry(owner.getDestinationId());
        } else {
            return fragment;
        }
    }
}
