package tech.ankainn.edanapplication.ui.formTwoB;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.binding.BindingAdapters;
import tech.ankainn.edanapplication.databinding.LayoutLivelihoodBinding;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class LivelihoodDialogFragment extends BottomSheetDialogFragment {

    private static final String ID_KEY = "id_key";

    private AutoClearedValue<LayoutLivelihoodBinding> binding;

    public static void showFragment(FragmentManager fm) {
        DialogFragment fragment = new LivelihoodDialogFragment();
        fragment.show(fm, "livelihood");
    }

    public static void showFragment(FragmentManager fm, long tempId) {
        Bundle args = new Bundle();
        args.putLong(ID_KEY, tempId);

        DialogFragment fragment = new LivelihoodDialogFragment();
        fragment.setArguments(args);
        fragment.show(fm, "livelihood");
    }

    private static long getIdFromBundle(Bundle args) {
        if (args == null) {
            return 0L;
        } else {
            return args.getLong(ID_KEY, 0L);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LayoutLivelihoodBinding binding = LayoutLivelihoodBinding.inflate(inflater, container, false);
        this.binding = new AutoClearedValue<>(binding, getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        long tempId = getIdFromBundle(getArguments());

        ViewModelProvider.Factory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        LivelihoodViewModel viewModel = new ViewModelProvider(this, factory).get(LivelihoodViewModel.class);

        viewModel.searchLivelihoodById(tempId);

        binding.get().textLivelihood.setOnItemClickListener((parent, view, position, id) -> {
            binding.get().textType.setText("", false);

            if(position != 5) {
                binding.get().textLivelihood.setInputType(InputType.TYPE_NULL);
            }

            int arrayId;

            switch (position) {
                case 0:
                    arrayId = R.array.farming;
                    break;
                case 1:
                    arrayId = R.array.livestock;
                    break;
                case 2:
                    arrayId = R.array.commerce;
                    break;
                case 3:
                    arrayId = R.array.tourism;
                    break;
                case 4:
                    arrayId = R.array.fishing;
                    break;
                default:
                    binding.get().textType.setAdapter(null);
                    binding.get().textType.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    return;
            }
            String[] array = getResources().getStringArray(arrayId);
            BindingAdapters.setDropdown(binding.get().textType, array, null);
        });

        binding.get().btnSave.setOnClickListener(v -> {
            dismiss();
            viewModel.pushActiveItem();
        });

        viewModel.getLivelihoodData().observe(getViewLifecycleOwner(), livelihoodData ->
                binding.get().setLivelihood(livelihoodData));
    }
}
