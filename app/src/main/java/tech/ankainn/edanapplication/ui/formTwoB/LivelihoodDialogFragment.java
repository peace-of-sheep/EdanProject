package tech.ankainn.edanapplication.ui.formTwoB;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.binding.BindingAdapters;
import tech.ankainn.edanapplication.databinding.LayoutLivelihoodBinding;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class LivelihoodDialogFragment extends BottomSheetDialogFragment {

    private static final String ID_KEY = "id_key";
    private static final String MEMBER_ID_KEY = "member_id_key";

    private AutoClearedValue<LayoutLivelihoodBinding> binding;

    public static void showFragment(FragmentManager fm, long tempMemberId) {
        Bundle args = new Bundle();
        args.putLong(MEMBER_ID_KEY, tempMemberId);

        DialogFragment fragment = new LivelihoodDialogFragment();
        fragment.setArguments(args);
        fragment.show(fm, "livelihood");
    }

    public static void showFragment(FragmentManager fm, long tempMemberId, long tempId) {
        Bundle args = new Bundle();
        args.putLong(MEMBER_ID_KEY, tempMemberId);
        args.putLong(ID_KEY, tempId);

        DialogFragment fragment = new LivelihoodDialogFragment();
        fragment.setArguments(args);
        fragment.show(fm, "livelihood");
    }

    private static long getTempId(Bundle args, String key) {
        if (args == null) {
            return 0L;
        } else {
            return args.getLong(key, 0L);
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

        long tempMemberId = getTempId(getArguments(), MEMBER_ID_KEY);
        long tempId = getTempId(getArguments(), ID_KEY);

        ViewModelProvider.Factory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        LivelihoodViewModel viewModel = new ViewModelProvider(this, factory).get(LivelihoodViewModel.class);

        viewModel.loadLivelihoodData(tempMemberId, tempId);

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
            BindingAdapters.setDropdown(binding.get().textType, array);
        });

        binding.get().btnSave.setOnClickListener(v -> {
            viewModel.saveLivelihoodData();
            dismiss();
        });

        viewModel.getLivelihoodData().observe(getViewLifecycleOwner(), livelihoodData ->
                binding.get().setLivelihood(livelihoodData));
    }
}
