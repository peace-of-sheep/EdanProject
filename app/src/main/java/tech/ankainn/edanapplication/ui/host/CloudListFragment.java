package tech.ankainn.edanapplication.ui.host;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import tech.ankainn.edanapplication.databinding.FragmentCloudListBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;

public class CloudListFragment extends BindingFragment<FragmentCloudListBinding> {

    /*@Override
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
    }*/
}
