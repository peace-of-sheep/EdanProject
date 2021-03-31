package tech.ankainn.edanapplication.ui.formTwoA;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentMemberBinding;
import tech.ankainn.edanapplication.global.Picker;
import tech.ankainn.edanapplication.global.PickerFragment;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.TextInputLayoutUtil;
import tech.ankainn.edanapplication.view.ProgressButton;

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
        /*binding().textCondition.setOnItemClickListener((p, v, pos, id) -> {
            viewModel.setCondition(requireContext(), pos);
        });*/
        binding().textPersonalInjury.setOnItemClickListener((p, v, pos, id) -> {
            if (pos == 0) {
                binding().textInjurySeverity.setEnabled(true);
            } else {
                binding().textInjurySeverity.setEnabled(false);
                binding().textInjurySeverity.setText("");
            }

            viewModel.setInjury(requireContext(), pos);
        });
        binding().textInjurySeverity.setOnItemClickListener((p, v, pos, id) -> {
            String item = (String) binding().textInjurySeverity.getAdapter().getItem(pos);
            viewModel.setInjurySeverity(item, pos);
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

        viewModel.getState().observe(getViewLifecycleOwner(), state -> {
            if (state == MemberViewModel.State.LOADING) {
                ProgressButton.showProgressUtil(binding().btnSearch, getString(R.string.loading));
            } else {
                ProgressButton.hideProgress(binding().btnSearch, getString(R.string.search));
            }
        });

        viewModel.getSingleEvent().observe(getViewLifecycleOwner(), state -> {
            if (state == MemberViewModel.State.ERROR) {
                Toast.makeText(requireContext(), "DNI not found", Toast.LENGTH_SHORT).show();
            }
        });

        /*viewModel.getHouseholdCondition().observe(getViewLifecycleOwner(),
                isAffected -> binding().textCondition.setEnabled(!isAffected));*/

        binding().pregnantSwitch.setOnCheckedChangeListener((view, isChecked) -> {
            viewModel.onPregnantChange(isChecked);
            binding().textPregnant.setEnabled(isChecked);
        });
    }
}
