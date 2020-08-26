package tech.ankainn.edanapplication.ui.host;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.util.Objects;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.LayoutItemFormTwoBinding;
import tech.ankainn.edanapplication.databinding.LayoutListBinding;
import tech.ankainn.edanapplication.global.Options;
import tech.ankainn.edanapplication.global.BottomOptionsFragment;
import tech.ankainn.edanapplication.model.formTwo.FormTwoSubset;
import tech.ankainn.edanapplication.ui.common.BindingAdapter2;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.Tuple2;
import tech.ankainn.edanapplication.viewmodel.FilesViewModelFactory;

public class FilesFragment extends BindingFragment<LayoutListBinding> {

    private FilesViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelStoreOwner owner = ScopeNavHostFragment.getOwner(this);
        FilesViewModelFactory factory = InjectorUtil.provideFilesViewModelFactory(requireContext());
        viewModel = new ViewModelProvider(owner, factory).get(FilesViewModel.class);

        BindingAdapter2<LayoutItemFormTwoBinding, Tuple2<Boolean, FormTwoSubset>> adapter =
                new BindingAdapter2<LayoutItemFormTwoBinding, Tuple2<Boolean, FormTwoSubset>>(
                        (oldItem, newItem) -> oldItem.second.id == newItem.second.id,
                        (oldItem, newItem) -> Objects.equals(oldItem.second.dataVersion, newItem.second.dataVersion),
                        (binding, data) -> {
                            binding.setFormTwoSubset(data.second);
                            binding.setLoading(data.first);
                        }
                ) {}
                .setOnItemCLick(itemBinding -> {
                    viewModel.setActiveFormTwoItem(itemBinding.getFormTwoSubset().id);
                    new BottomOptionsFragment.Builder("item")
                            .setTitle(R.string.options)
                            .addOption(R.string.upload, R.drawable.ic_cloud_upload_24)
                            .addOption(R.string.open_file, R.drawable.ic_folder_24dp)
                            .build(getParentFragmentManager());
                }).addBindingPayload("loading", (binding, data) -> binding.setLoading(data.first));

        binding().recyclerView.setAdapter(adapter);

        viewModel.getListFormTwo().observe(getViewLifecycleOwner(), adapter::submitList);

        Options.getInstance().observe(getViewLifecycleOwner(), (emitter, option) -> {
            if ("item".equals(emitter)) {
                if (option == 1) {
                    NavDirections action = HostFragmentDirections.actionHostToFormTwo()
                            .setFormTwoId(viewModel.getActiveFormTwoItem());
                    Navigation.findNavController(requireActivity(), R.id.fragment_container)
                            .navigate(action);
                }
            }
        });
    }
}
