package tech.ankainn.edanapplication.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.ankainn.edanapplication.BuildConfig;

public class RetrofitUtil {



    private RetrofitUtil() {

    }

    public static EdanApiService createApiService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        if(BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                /*.addInterceptor(healthInterceptor)
                .addInterceptor(jwtInterceptor)*/
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder()
                //.baseUrl("https://edan.anka.pe/api/v1/")
                .baseUrl("http://64.227.22.60:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                /*.callbackExecutor()*/
                .build()
                .create(EdanApiService.class);
    }
}
