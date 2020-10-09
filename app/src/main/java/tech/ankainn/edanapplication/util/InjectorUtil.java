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
import tech.ankainn.edanapplication.repositories.UserRepository;
import tech.ankainn.edanapplication.viewmodel.EdanViewModelFactory;
import tech.ankainn.edanapplication.viewmodel.FormOneViewModelFactory;

public class InjectorUtil {

    private static UserRepository getUserRepository(Context context) {
        return UserRepository.getInstance(AppExecutors.getInstance(),
                ApiService.getGaldosService(), EdanDatabase.getInstance(context));
    }

    private static GenInfRepository getGenInfRepository(Context context) {
        return GenInfRepository.getInstance(AppExecutors.getInstance(),
                LocationServices.getFusedLocationProviderClient(context),
                Cache.getInstance());
    }

    private static FormOneRepository getFormOneRepository(Context context) {
        return FormOneRepository.getInstance(AppExecutors.getInstance(),
                ApiService.getEdanService(),
                EdanDatabase.getInstance(context.getApplicationContext()),
                Cache.getInstance());
    }

    private static FormTwoRepository getFormTwoRepository(Context context) {
        return FormTwoRepository.getInstance(AppExecutors.getInstance(),
                ApiService.getEdanService(),
                ApiService.getReniecService(),
                ApiService.getGaldosService(),
                EdanDatabase.getInstance(context.getApplicationContext()),
                Cache.getInstance());
    }

    public static FormOneViewModelFactory provideFormOneViewModelFactory(Context context) {
        return new FormOneViewModelFactory(getFormOneRepository(context));
    }

    public static EdanViewModelFactory provideViewModelFactory(Context context) {
        return new EdanViewModelFactory(getUserRepository(context), getGenInfRepository(context),
                getFormOneRepository(context), getFormTwoRepository(context));
    }
}
