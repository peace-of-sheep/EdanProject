package tech.ankainn.edanapplication.util;

import android.content.Context;

import com.google.android.gms.location.LocationServices;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.danger.DangerSource;
import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.repositories.Cache;
import tech.ankainn.edanapplication.repositories.FormOneRepository;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.repositories.GenInfRepository;
import tech.ankainn.edanapplication.api.ApiService;
import tech.ankainn.edanapplication.repositories.LivelihoodRepository;
import tech.ankainn.edanapplication.repositories.MemberRepository;
import tech.ankainn.edanapplication.repositories.ReniecRepository;
import tech.ankainn.edanapplication.repositories.UbigeoDangerRepository;
import tech.ankainn.edanapplication.repositories.UserRepository;
import tech.ankainn.edanapplication.viewmodel.EdanViewModelFactory;
import tech.ankainn.edanapplication.viewmodel.FormOneViewModelFactory;
import tech.ankainn.edanapplication.viewmodel.FormTwoViewModelFactory;
import tech.ankainn.edanapplication.viewmodel.GenInfViewModelFactory;

public class InjectorUtil {

    private static UserRepository getUserRepository(Context context) {
        return UserRepository.getInstance(AppExecutors.getInstance(), Cache.getInstance(),
                ApiService.getGaldosService(), EdanDatabase.getInstance(context));
    }

    private static GenInfRepository getGenInfRepository(Context context) {
        return GenInfRepository.getInstance(AppExecutors.getInstance(),
                LocationServices.getFusedLocationProviderClient(context),
                DangerSource.getInstance(),
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
                ApiService.getGaldosService(),
                EdanDatabase.getInstance(context.getApplicationContext()),
                Cache.getInstance());
    }

    private static MemberRepository getMemberRepository() {
        return MemberRepository.getInstance(Cache.getInstance());
    }

    private static LivelihoodRepository getLivelihoodRepository() {
        return LivelihoodRepository.getInstance(Cache.getInstance());
    }

    private static ReniecRepository getReniecRepository() {
        return ReniecRepository.getInstance(AppExecutors.getInstance(), ApiService.getReniecService());
    }

    private static UbigeoDangerRepository getUbigeoDangerRepository(Context context) {
        return UbigeoDangerRepository.getInstance(AppExecutors.getInstance(), ApiService.getGaldosService(),
                EdanDatabase.getInstance(context.getApplicationContext()).ubigeoDao());
    }

    public static FormOneViewModelFactory provideFormOneViewModelFactory(Context context) {
        return new FormOneViewModelFactory(getFormOneRepository(context));
    }

    public static FormTwoViewModelFactory provideFormTwoViewModelFactory(Context context) {
        return new FormTwoViewModelFactory(getFormTwoRepository(context), getMemberRepository(),
                getLivelihoodRepository(), getReniecRepository());
    }

    public static EdanViewModelFactory provideViewModelFactory(Context context) {
        return new EdanViewModelFactory(getUserRepository(context), getFormOneRepository(context),
                getFormTwoRepository(context));
    }

    public static GenInfViewModelFactory provideGenInfViewModelFactory(Context context) {
        return new GenInfViewModelFactory(getGenInfRepository(context), getUbigeoDangerRepository(context));
    }
}
