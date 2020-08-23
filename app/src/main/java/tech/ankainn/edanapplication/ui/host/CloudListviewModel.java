package tech.ankainn.edanapplication.ui.host;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.retrofit.ApiListResponse;
import tech.ankainn.edanapplication.ui.common.BaseViewModel;

public class CloudListviewModel extends ViewModel {

    private FormTwoRepository formTwoRepository;

    public CloudListviewModel() {

    }
}
