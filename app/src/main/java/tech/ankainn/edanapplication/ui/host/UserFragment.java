package tech.ankainn.edanapplication.ui.host;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import tech.ankainn.edanapplication.databinding.FragmentUserBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;

public class UserFragment extends BindingFragment<FragmentUserBinding> {

    @Override
    protected FragmentUserBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentUserBinding.inflate(inflater, container, false);
    }
}
