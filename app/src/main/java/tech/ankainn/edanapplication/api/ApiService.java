package tech.ankainn.edanapplication.api;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.ankainn.edanapplication.BuildConfig;

public class ApiService {

    private static final ApiService ourInstance = new ApiService();

    private EdanApiService service;
    private ReniecService reniecService;
    private GaldosService galdosService;

    public static EdanApiService getEdanService() {
        return ourInstance.service;
    }

    public static ReniecService getReniecService() {
        return ourInstance.reniecService;
    }

    public static GaldosService getGaldosService() {
        return ourInstance.galdosService;
    }

    private ApiService() {
        service = createEdanApi();
        reniecService = createReniecService();
        galdosService = createGaldosService();
    }

    private ReniecService createReniecService() {
        String baseUrl = BuildConfig.RENIEC_API_URL;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ReniecService.class);
    }

    private EdanApiService createEdanApi() {
        String baseUrl = BuildConfig.EDAN_API_URL;
        String user = BuildConfig.EDAN_API_USER;
        String pass = BuildConfig.EDAN_API_PASSWORD;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request.Builder requestBuilder = chain.request().newBuilder();
                    requestBuilder.header("Content-Type", "application/json");
                    requestBuilder.header("Authorization", "Basic "+btoa(user+":"+pass));
                    return chain.proceed(requestBuilder.build());
                })
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(EdanApiService.class);
    }

    private GaldosService createGaldosService() {
        String baseUrl = BuildConfig.GALDOS_API_URL;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(GaldosService.class);
    }

    private String btoa(String value) {
        return Base64.encodeToString(value.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
    }
}
