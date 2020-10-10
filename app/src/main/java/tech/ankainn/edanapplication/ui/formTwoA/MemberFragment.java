package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import tech.ankainn.edanapplication.databinding.FragmentMemberBinding;
import tech.ankainn.edanapplication.global.Picker;
import tech.ankainn.edanapplication.global.PickerFragment;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.TextInputLayoutUtil;

public class MemberFragment extends BindingFragment<FragmentMemberBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        long tempId = MemberFragmentArgs.fromBundle(requireArguments()).getMemberId();

        ViewModelProvider.Factory factory = InjectorUtil.provideFormTwoViewModelFactory(requireContext());
        MemberViewModel viewModel = new ViewModelProvider(this, factory).get(MemberViewModel.class);

        viewModel.loadMemberData(tempId);

        TextInputLayoutUtil.setOnClickListener(binding().layoutBirthdate,
                v -> PickerFragment.showPicker(PickerFragment.MODE_DATE, this));

        binding().btnSearch.setOnClickListener(v -> {
            if (!viewModel.searchPersonByDni()) {
                Toast.makeText(requireContext(), "Not valid", Toast.LENGTH_SHORT).show();
            }
        });

        binding().textIdType.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setDocumentType(requireContext(), pos);
        });
        binding().textGender.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setGender(requireContext(), pos);
        });
        binding().textCondition.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setCondition(requireContext(), pos);
        });
        binding().textPersonalInjury.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setInjury(requireContext(), pos);
        });

        Picker.getInstance().observe(getViewLifecycleOwner(), (emitter, value) -> {
            if ("date".equals(emitter)) {
                binding().textBirthdate.setText(value);
            }
        });

        binding().btnCancel.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
        binding().btnSave.setOnClickListener(v -> {
            if (viewModel.saveMemberData()) {
                NavHostFragment.findNavController(this).popBackStack();
            } else {
                Toast.makeText(requireContext(), "Complete data", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getMemberData().observe(getViewLifecycleOwner(), memberData -> binding().setMember(memberData));
    }
}
