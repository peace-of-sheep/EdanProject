package tech.ankainn.edanapplication.global;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BottomOptionsFragment extends BottomSheetDialogFragment {

    private static final String KEY_EMITTER = "key_emitter";
    private static final String KEY_TTL = "key_ttl";
    private static final String KEY_OPTIONS_NUM = "key_options_n";
    private static final String KEY_OPTIONS_TEXT = "key_options_text";
    private static final String KEY_OPTIONS_DRAWABLES = "key_options_drawables";

    @StringRes
    private static final int defaultIdTitle = R.string.options;

    @DrawableRes
    private static final int defaultIdDrawable = R.drawable.ic_folder_24dp;

    private static boolean open = false;

    private String emitter;

    private OnOption listener = pos -> new Handler().postDelayed(() -> {
        Options.getInstance().setOption(emitter, pos);
        dismiss();
    }, 100L);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int dim16dp = getResources().getDimensionPixelSize(R.dimen.dim_big);
        int dim24dp = getResources().getDimensionPixelSize(R.dimen.dim_xbig);
        int colorPrimary = ContextCompat.getColor(requireContext(), R.color.colorPrimary);
        float text16sp = getResources().getDimension(R.dimen.text_big);

        TypedValue value = new TypedValue();
        requireActivity().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, value, true);

        // args
        Bundle args = getArguments();
        if(args == null) args = new Bundle();

        emitter = args.getString(KEY_EMITTER, getTag());

        // root
        LinearLayout root = new LinearLayout(requireContext());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        root.setLayoutParams(lp);
        root.setOrientation(LinearLayout.VERTICAL);

        // title
        MaterialTextView title = new MaterialTextView(requireContext());
        ViewGroup.LayoutParams lpTitle = new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        title.setLayoutParams(lpTitle);

        title.setPaddingRelative(dim16dp, dim24dp, dim16dp, dim24dp);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, text16sp);
        title.setTextColor(colorPrimary);

        int idTitle = args.getInt(KEY_TTL, defaultIdTitle);
        title.setText(idTitle);

        root.addView(title);

        // buttons
        int n = args.getInt(KEY_OPTIONS_NUM, 0);
        int[] texts = args.getIntArray(KEY_OPTIONS_TEXT);
        int[] drawables = args.getIntArray(KEY_OPTIONS_DRAWABLES);
        for (int i = 0; i < n; i++) {
            MaterialTextView option = new MaterialTextView(requireContext());
            ViewGroup.LayoutParams lpOption = new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            option.setLayoutParams(lpOption);

            option.setPaddingRelative(dim16dp, dim16dp, dim16dp, dim16dp);
            option.setTextSize(TypedValue.COMPLEX_UNIT_PX, text16sp);
            option.setBackgroundResource(value.resourceId);

            Drawable drawable = ContextCompat.getDrawable(requireContext(), drawables != null ? drawables[i] : defaultIdDrawable);
            option.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
            option.setCompoundDrawablePadding(dim24dp);
            option.setText(texts != null ? texts[i] : defaultIdTitle);

            int pos = i;
            option.setOnClickListener(v -> listener.onOption(pos));

            root.addView(option);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!requireActivity().isChangingConfigurations()) {
            open = false;
        }
    }

    private interface OnOption {
        void onOption(int pos);
    }

    public static class Builder {

        private String emitter;

        @StringRes
        private int idTitle = defaultIdTitle;

        @StringRes
        private List<Integer> idOptions = new ArrayList<>();
        private List<Integer> idDrawables = new ArrayList<>();

        public Builder(String emitter) {
            this.emitter = emitter;
        }

        public Builder setTitle(@StringRes int title) {
            idTitle = title;
            return this;
        }

        public Builder addOption(@StringRes int text, @DrawableRes int drawable) {
            idOptions.add(text);
            idDrawables.add(drawable);
            return this;
        }

        public void build(FragmentManager fm) {
            if(!open) {
                open = true;

                Bundle args = new Bundle();
                args.putString(KEY_EMITTER, emitter);
                args.putInt(KEY_TTL, idTitle);

                int n = idOptions.size();
                if(n > 0) {
                    int[] tempIdOptions = new int[n];
                    int[] tempIdDrawables = new int[n];

                    for (int i = 0; i < n; i++) {
                        tempIdOptions[i] = idOptions.get(i);
                        tempIdDrawables[i] = idDrawables.get(i);
                    }

                    args.putIntArray(KEY_OPTIONS_TEXT, tempIdOptions);
                    args.putIntArray(KEY_OPTIONS_DRAWABLES, tempIdDrawables);
                }
                args.putInt(KEY_OPTIONS_NUM, n);

                DialogFragment fragment = new BottomOptionsFragment();
                fragment.setArguments(args);
                fragment.show(fm, "options");
            }
        }
    }
}
