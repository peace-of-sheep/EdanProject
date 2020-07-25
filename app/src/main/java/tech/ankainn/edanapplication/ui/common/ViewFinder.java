package tech.ankainn.edanapplication.ui.common;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Util interface for holders/fragments with data binding that needs a quick way to find child views.
 * Override {@link #findViewById}
 * <p>
 * Typical usage
 * <pre>
 * public class SomeFragment implements ViewFinder {
 *
 *     // ...
 *     // fragment code
 *     // ...
 *
 *     // nonnull
 *     // override
 *     public View rootView() {
 *         return requireView();
 *     }
 *
 *     // override
 *     public View findViewById(int viewId) {
 *         if(viewId == R.id.someView) return binding.someView;
 *         if(viewId == R.id.otherView) return binding.otherView;
 *         return super.findViewById(viewId);
 *     }
 * }
 * </pre>
 * </>
 */
public interface ViewFinder {

    /**
     * Call holders/fragment root view, useful to find child view using {@link #findViewById}.
     * @return Holder/Fragment root {@link View}
     */
    @Nullable
    View rootView();

    /**
     * Find child View from holder/fragment root view.
     * Override this method to find quickly child View, otherwise let Android Framework use default method.
     *
     * @return A child {@link View}
     */
    @Nullable
    default <T extends View> T findViewById(int viewId) {
        View root = rootView();
        if(root != null)
            root.findViewById(viewId);
        return null;
    }
}
