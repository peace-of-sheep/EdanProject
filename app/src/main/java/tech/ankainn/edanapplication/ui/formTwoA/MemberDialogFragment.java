package tech.ankainn.edanapplication.ui.formTwoA;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.binding.BindingAdapters;
import tech.ankainn.edanapplication.binding.Converter;
import tech.ankainn.edanapplication.databinding.LayoutMemberBinding;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.InjectorUtil;

import static tech.ankainn.edanapplication.ui.formTwoA.Utilities.searchIfCorrectType;
import static tech.ankainn.edanapplication.ui.formTwoA.Utilities.setPersonData;

public class MemberDialogFragment extends BottomSheetDialogFragment {

    private AutoClearedValue<LayoutMemberBinding> binding;

    private MembersViewModel viewModel;

    public static void showFragment(FragmentManager fm) {
        DialogFragment fragment = new MemberDialogFragment();
        fragment.show(fm, "add");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutMemberBinding viewBinding = LayoutMemberBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(viewBinding, getViewLifecycleOwner());
        return viewBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideViewModelFactory(requireContext());
        viewModel = new ViewModelProvider(this, factory).get(MembersViewModel.class);

        viewModel.getDialogMemberData().observe(getViewLifecycleOwner(), memberData ->
                binding.get().setMember(memberData));

        binding.get().textIdType.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setDocumentType(requireContext(), pos);
        });
        binding.get().textGender.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setGender(requireContext(), pos);
        });
        binding.get().textCondition.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setCondition(requireContext(), pos);
        });
        binding.get().textPersonalInjury.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setInjury(requireContext(), pos);
        });

        binding.get().btnCancel.setOnClickListener(v -> dismiss());
        binding.get().btnSave.setOnClickListener(v -> {
            if (binding.get().getMember().notEmpty()) {
                viewModel.pushActiveMemberData();
                dismiss();
            } else {
                // todo redo
                Toast.makeText(requireContext(), "More content", Toast.LENGTH_SHORT).show();
            }
        });

        binding.get().btnSearch.setOnClickListener(v -> searchIfCorrectType(viewModel,
                binding.get().textIdType, binding.get().textIdNumber, getString(R.string.dni)));

        viewModel.getPersonReniecData().observe(getViewLifecycleOwner(), reniecData -> {
            if (reniecData != null) {
                String[] surnames = reniecData.apellidos.split(",");
                String surname = surnames[0] + surnames[1];

                binding.get().textSurname.setText(surname);
                binding.get().textName.setText(reniecData.nombres);
                binding.get().textAge.setText(reniecData.edad);
                binding.get().textBirthdate.setText(reniecData.birthdate);

                String[] genders = getResources().getStringArray(R.array.gender);
                String gender = reniecData.tipoSexo.equals("M") ? genders[0] : genders[1];
                BindingAdapters.setTextDropDown(binding.get().textGender, gender);

                viewModel.setGender(requireContext(), 0);
                viewModel.setDocumentType(requireContext(), 0);
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        viewModel.clearMemberData();
        super.onDismiss(dialog);
    }
}
