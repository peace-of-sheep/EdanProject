package tech.ankainn.edanapplication.util;

import android.content.Context;

import com.google.android.gms.location.LocationServices;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.repositories.Cache;
import tech.ankainn.edanapplication.repositories.FormOneRepository;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.repositories.GenInfRepository;
import tech.ankainn.edanapplication.api.ApiService;
import tech.ankainn.edanapplication.viewmodel.FilesViewModelFactory;
import tech.ankainn.edanapplication.viewmodel.FormOneViewModelFactory;
import tech.ankainn.edanapplication.viewmodel.FormTwoViewModelFactory;
import tech.ankainn.edanapplication.viewmodel.GenInfViewModelFactory;

public class InjectorUtil {

    private static GenInfRepository getGenInfRepository(Context context) {
        return GenInfRepository.getInstance(AppExecutors.getInstance(),
                LocationServices.getFusedLocationProviderClient(context),
                Cache.getInstance());
    }

    private static FormOneRepository getFormOneRepository(Context context) {
        return FormOneRepository.getInstance(AppExecutors.getInstance(),
                ApiService.getService(),
                EdanDatabase.getInstance(context.getApplicationContext()),
                Cache.getInstance());
    }

    private static FormTwoRepository getFormTwoRepository(Context context) {
        return FormTwoRepository.getInstance(AppExecutors.getInstance(),
                ApiService.getService(),
                EdanDatabase.getInstance(context.getApplicationContext()),
                Cache.getInstance());
    }

    public static FilesViewModelFactory provideFilesViewModelFactory(Context context) {
        return new FilesViewModelFactory(getFormOneRepository(context), getFormTwoRepository(context));
    }

    public static FormTwoViewModelFactory provideFormTwoViewModelFactory(Context context) {
        return new FormTwoViewModelFactory(getFormTwoRepository(context));
    }

    public static FormOneViewModelFactory provideFormOneViewModelFactory(Context context) {
        return new FormOneViewModelFactory(getFormOneRepository(context));
    }

    public static GenInfViewModelFactory provideGenInfViewModelFactory(Context context) {
        return new GenInfViewModelFactory(getGenInfRepository(context));
    }
}
