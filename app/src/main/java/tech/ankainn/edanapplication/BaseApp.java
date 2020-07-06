package tech.ankainn.edanapplication;

import android.app.Application;

import tech.ankainn.edanapplication.repositories.UserRepository;
import tech.ankainn.edanapplication.retrofit.ApiService;
import tech.ankainn.edanapplication.retrofit.RetrofitUtil;
import tech.ankainn.edanapplication.util.CrashReportingTree;
import timber.log.Timber;

public class BaseApp extends Application {

    private AppExecutors appExecutors;

    private ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(BuildConfig.DEBUG ? new Timber.DebugTree() : new CrashReportingTree());

        appExecutors = new AppExecutors();

        apiService = RetrofitUtil.createApiService();
    }

    // public methods
    public UserRepository getUserRepository() {
        return UserRepository.getInstance(appExecutors, apiService);
    }

    public AppExecutors getAppExecutors() {
        return appExecutors;
    }
}