package tech.ankainn.edanapplication.ui.formTwoB;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import tech.ankainn.edanapplication.databinding.LayoutLivelihoodBinding;
import tech.ankainn.edanapplication.model.app.master.DataEntity;
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

        viewModel.getLivelihoodNames().observe(getViewLifecycleOwner(),
                livelihoodNames -> binding.get().setLivelihoodList(livelihoodNames));
        viewModel.getLivelihoodTypeNames().observe(getViewLifecycleOwner(),
                livelihoodTypeNames -> binding.get().setTypeList(livelihoodTypeNames));

        binding.get().textLivelihood.setOnItemClickListener((p, v, pos, id) -> {
            binding.get().textType.setText(null);

            DataEntity data = (DataEntity) binding.get().textLivelihood.getAdapter().getItem(pos);
            viewModel.onLivelihoodName(data);
        });
        binding.get().textType.setOnItemClickListener((p, v, pos, id) -> {
            DataEntity data = (DataEntity) binding.get().textType.getAdapter().getItem(pos);
            viewModel.onLivelihoodTypeName(data);
        });

        viewModel.getLivelihoodData().observe(getViewLifecycleOwner(), livelihoodData ->
                binding.get().setLivelihood(livelihoodData));

        binding.get().btnSave.setOnClickListener(v -> {
            viewModel.saveLivelihoodData();
            dismiss();
        });
    }
}
