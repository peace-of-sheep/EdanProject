package tech.ankainn.edanapplication.ui.host;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.gson.Gson;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentFilesBinding;
import tech.ankainn.edanapplication.global.BottomOptions;
import tech.ankainn.edanapplication.global.BottomOptionsFragment;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class FilesFragment extends BindingFragment<FragmentFilesBinding> {

    private FilesViewModel viewModel;

    @Override
    protected FragmentFilesBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentFilesBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(FilesViewModel.class);

        FilesAdapter adapter = new FilesAdapter((formTwoData, loading) -> {
            Timber.tag(Tagger.DUMPER).d("onActivityCreated: %s", new Gson().toJson(formTwoData));
            viewModel.setActiveFile(formTwoData, loading);
            new BottomOptionsFragment.Builder("item")
                    .setTitle(R.string.options)
                    .addOption(R.string.upload, R.drawable.ic_cloud_upload_24)
                    .addOption(R.string.open_file, R.drawable.ic_folder_24dp)
                    .build(getParentFragmentManager());
        });
        binding().recyclerView.setAdapter(adapter);

        binding().frameAdd.setOnClickListener(v ->
                new BottomOptionsFragment.Builder("select")
                        .setTitle(R.string.select_form)
                        .addOption(R.string.form_one, R.drawable.ic_folder_24dp)
                        .addOption(R.string.form_two, R.drawable.ic_folder_24dp)
                        .build(getParentFragmentManager()));

        viewModel.getList().observe(getViewLifecycleOwner(), adapter::replace);

        BottomOptions.getInstance().observe(getViewLifecycleOwner(), (emitter, option) -> {
            switch (emitter) {
                case "select":
                    if (option == 1) {
                        viewModel.createFormTwoFile();
                        Navigation.findNavController(requireActivity(), R.id.fragment_container)
                                .navigate(HostFragmentDirections.actionHostToFormTwo());
                    }
                    return;
                case "item":
            }
        });
    }
}
