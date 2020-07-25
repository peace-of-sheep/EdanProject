package tech.ankainn.edanapplication.ui.hostmain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.databinding.FragmentCloudListBinding;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.util.FormTwoFactory;
import tech.ankainn.edanapplication.util.Metadata;

public class CloudListFragment extends BindingFragment<FragmentCloudListBinding> {

    @Override
    protected FragmentCloudListBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCloudListBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CloudListviewModel cloudListviewModel = new ViewModelProvider(this).get(CloudListviewModel.class);

        FilesAdapter adapter = new FilesAdapter((binding, pos) -> {});
        binding().recyclerView.setAdapter(adapter);

        // TODO remove this
        binding().setLoading(true);
        cloudListviewModel.getList().observe(getViewLifecycleOwner(), data -> {
            binding().setLoading(false);

            if (data != null) {
                List<FormTwoData> temp = FormTwoFactory.fromApiList(data);
                List<Metadata<FormTwoData>> result = new ArrayList<>();
                for (FormTwoData formTwoData : temp) {
                    result.add(new Metadata<>(formTwoData));
                }
                adapter.replace(result);
            } else {
                // TODO redo
                Toast.makeText(requireContext(), "No hay elementos", Toast.LENGTH_SHORT).show();
            }
        });

        binding().swipe.setOnRefreshListener(() -> {
            cloudListviewModel.getList().observe(getViewLifecycleOwner(), data -> {
                binding().swipe.setRefreshing(false);

                if (data != null) {
                    List<FormTwoData> temp = FormTwoFactory.fromApiList(data);
                    List<Metadata<FormTwoData>> result = new ArrayList<>();
                    for (FormTwoData formTwoData : temp) {
                        result.add(new Metadata<>(formTwoData));
                    }
                    adapter.replace(result);
                } else {
                    // TODO redo
                    Toast.makeText(requireContext(), "No hay elementos", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
