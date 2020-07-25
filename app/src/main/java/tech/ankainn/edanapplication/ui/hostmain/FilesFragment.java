package tech.ankainn.edanapplication.ui.hostmain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentFilesBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.AutoClearedValue;
import tech.ankainn.edanapplication.util.Metadata;

import static tech.ankainn.edanapplication.ui.hostmain.FileOptionsDialogFragment.Option.OPEN;
import static tech.ankainn.edanapplication.ui.hostmain.FileOptionsDialogFragment.Option.UPLOAD;

public class FilesFragment extends BindingFragment<FragmentFilesBinding> implements FileOptionsDialogFragment.OptionCallback {

    private FilesViewModel viewModel;
    private AutoClearedValue<FilesAdapter> adapter;

    @Override
    protected FragmentFilesBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentFilesBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(FilesViewModel.class);

        FilesAdapter filesAdapter = new FilesAdapter((formTwoData, loading) -> {
            viewModel.setActiveItem(new Metadata<>(formTwoData, loading));
            FileOptionsDialogFragment.showFragment(this);
        });
        binding().recyclerView.setAdapter(filesAdapter);
        adapter = new AutoClearedValue<>(filesAdapter, getViewLifecycleOwner());
        viewModel.getList().observe(getViewLifecycleOwner(), list -> adapter.get().replace(list));

        viewModel.getMessenger().observe(getViewLifecycleOwner(), messenger -> {
            String msg = messenger.getMessageIfNotHandled();
            if (msg != null) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        binding().frameAdd.setOnClickListener(v -> {
            DialogFragment fragment = new SelectFormDialogFragment();
            fragment.show(getParentFragmentManager(), "select");
        });
    }

    @Override
    public void onOption(FileOptionsDialogFragment.Option option) {
        if (option == UPLOAD) {
            viewModel.uploadFile();
        } else if(option == OPEN) {
            viewModel.openFile();
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(HostMainFragmentDirections.actionMainToFormTwo());
        }
        viewModel.clearActiveItem();
    }
}
