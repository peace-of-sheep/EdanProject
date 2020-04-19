package tech.ankainn.edanapplication.ui.formOne;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import tech.ankainn.edanapplication.databinding.FragmentSwitchableBinding;
import tech.ankainn.edanapplication.ui.base.BaseFragment;
import tech.ankainn.edanapplication.util.AutoClearedValue;

public class SwitchableFragment extends BaseFragment {

    private static final String ARRAY_KEY = "array_key";
    private static final String TITLE_KEY = "title_key";
    private static final String QUANTITY_VISIBILITY_KEY = "quantity_key";
    private static final String SWITCH_VISIBILITY_KEY = "switch_key";
    private static final String BOTTOM_VISIBILITY_KEY = "bottom_key";
    private static final String BTN_TEXT_KEY = "btn_key";

    @ArrayRes
    private int arrayId;
    @StringRes
    private int titleId;

    private boolean quantityVisibility;
    private boolean switchVisibility;

    private boolean bottomVisibility;
    private String btnText;

    private AutoClearedValue<FragmentSwitchableBinding> binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            arrayId = args.getInt(ARRAY_KEY);
            titleId = args.getInt(TITLE_KEY);
            quantityVisibility = args.getBoolean(QUANTITY_VISIBILITY_KEY);
            switchVisibility = args.getBoolean(SWITCH_VISIBILITY_KEY);
            bottomVisibility = args.getBoolean(BOTTOM_VISIBILITY_KEY);
            if(bottomVisibility) btnText = args.getString(BTN_TEXT_KEY);
        }
    }

    @NonNull
    @Override
    protected View makeView(LayoutInflater inflater, ViewGroup container) {
        final FragmentSwitchableBinding bindingTemp =
                FragmentSwitchableBinding.inflate(inflater, container, false);
        bindingTemp.setLifecycleOwner(getViewLifecycleOwner());
        binding = new AutoClearedValue<>(bindingTemp);
        getViewLifecycleOwner().getLifecycle().addObserver(binding);
        return bindingTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(arrayId == 0) return;

        SwitchableViewModel viewModel = new ViewModelProvider(this).get(SwitchableViewModel.class);



        String title = getString(titleId);

        String[] strings = getResources().getStringArray(arrayId);
        SwitchableAdapter adapter = new SwitchableAdapter(strings, quantityVisibility, switchVisibility);

        binding.get().setBottomVisibility(bottomVisibility);
        if(bottomVisibility) binding.get().setBtnText(btnText);
        binding.get().setTitle(title);
        binding.get().setAdapter(adapter);
    }

    private static class NoScrollLayoutManager extends LinearLayoutManager {

        public NoScrollLayoutManager(Context context) {
            super(context);
        }

        public NoScrollLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public NoScrollLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public boolean canScrollVertically() {
            return false;
        }
    }

    public static SwitchableFragment create(@StringRes int titleId, @ArrayRes int arrayId,
                                            boolean bottomVisibility, String btnText,
                                            boolean quantityVisibility,
                                            boolean switchVisibility) {
        Bundle args = new Bundle();
        args.putInt(ARRAY_KEY, arrayId);
        args.putInt(TITLE_KEY, titleId);
        args.putBoolean(QUANTITY_VISIBILITY_KEY, quantityVisibility);
        args.putBoolean(SWITCH_VISIBILITY_KEY, switchVisibility);
        args.putBoolean(BOTTOM_VISIBILITY_KEY, bottomVisibility);
        if(bottomVisibility) args.putString(BTN_TEXT_KEY, btnText);

        SwitchableFragment fragment = new SwitchableFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
