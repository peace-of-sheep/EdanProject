package tech.ankainn.edanapplication.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static final ApiService ourInstance = new ApiService();

    private EdanApiService service;

    public static EdanApiService getService() {
        return ourInstance.service;
    }

    private ApiService() {
        service = create();
    }

    private EdanApiService create() {
        String baseUrl = "http://64.227.22.60:3000/";

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
                .create(EdanApiService.class);
    }
}
