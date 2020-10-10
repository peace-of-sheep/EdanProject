package tech.ankainn.edanapplication.ui.host;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.LayoutItemFormTwoBinding;
import tech.ankainn.edanapplication.databinding.LayoutListBinding;
import tech.ankainn.edanapplication.global.Options;
import tech.ankainn.edanapplication.global.BottomOptionsFragment;
import tech.ankainn.edanapplication.model.dto.FormTwoSubset;
import tech.ankainn.edanapplication.ui.common.BindingAdapter2;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;
import tech.ankainn.edanapplication.util.Tuple2;
import timber.log.Timber;

public class ListFormTwoFragment extends BindingFragment<LayoutListBinding> {

    private FilesViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideViewModelFactory(requireContext());
        viewModel = new ViewModelProvider(this, factory).get(FilesViewModel.class);

        BindingAdapter2<LayoutItemFormTwoBinding, Tuple2<Boolean, FormTwoSubset>> adapter =
                new BindingAdapter2<LayoutItemFormTwoBinding, Tuple2<Boolean, FormTwoSubset>>(
                        (oldItem, newItem) -> oldItem.second.id == newItem.second.id,
                        (oldItem, newItem) -> Objects.equals(oldItem.second.dataVersion, newItem.second.dataVersion)
                                && Objects.equals(oldItem.first, newItem.first),
                        (binding, data) -> {
                            binding.setFormTwoSubset(data.second);
                            binding.setLoading(data.first);
                            setCardColor(binding.colorView, data.second.formTwoApiId);
                            setTextForRemoteId(binding.textRemoteId, data.second.formTwoApiId);
                        }
                ) {}
                .setOnItemCLick((pos, itemBinding) -> {
                    viewModel.setActiveFormTwoItem(pos, itemBinding.getFormTwoSubset().id);
                    new BottomOptionsFragment.Builder("item")
                            .setTitle(R.string.options)
                            .addOption(R.string.upload, R.drawable.ic_cloud_upload_24)
                            .addOption(R.string.open_file, R.drawable.ic_folder_24dp)
                            .build(getParentFragmentManager());
                });
        binding().recyclerView.setAdapter(adapter);

        binding().setVisible(true);

        viewModel.getListFormTwo().observe(getViewLifecycleOwner(), list -> {
            binding().setVisible(list == null || list.isEmpty());
            adapter.submitList(list);
        });

        Options.getInstance().observe(getViewLifecycleOwner(), (emitter, option) -> {
            if ("item".equals(emitter)) {
                long id = viewModel.getActiveFormTwoItemId();
                switch (option) {
                    case -1:
                        break;
                    case 0:
                        viewModel.uploadFormTwo(id);
                        int pos = viewModel.getTempPos();
                        setLoadingItem(adapter, pos, true);
                        break;
                    case 1:
                        NavDirections action = HostFragmentDirections.actionHostToFormTwo()
                                .setFormTwoId(id);
                        Navigation.findNavController(requireActivity(), R.id.fragment_container)
                                .navigate(action);
                }
            }
        });
    }

    private void setLoadingItem(BindingAdapter2<?, Tuple2<Boolean, FormTwoSubset>> adapter, int pos, boolean loading) {
        List<Tuple2<Boolean, FormTwoSubset>> list = new ArrayList<>(adapter.getCurrentList());
        list.set(pos, new Tuple2<>(loading, list.get(pos).second));
        adapter.submitList(list);
    }

    private static void setCardColor(View colorView, Integer apiId) {
        Context context = colorView.getContext();

        if (apiId == -1) {
            Resources.Theme theme = context.getTheme();

            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(R.attr.colorError, typedValue, true);
            colorView.setBackgroundColor(typedValue.data);
        } else {
            int colorGreen = ContextCompat.getColor(context, android.R.color.holo_green_dark);
            colorView.setBackgroundColor(colorGreen);
        }
    }

    private void setTextForRemoteId(TextView view, Integer value) {
        if (value == -1) {
            view.setText(getString(R.string.not_sent_args));
        } else {
            view.setText(getString(R.string.sent_args, value));
        }
    }
}
