package tech.ankainn.edanapplication.retrofit;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.ankainn.edanapplication.BuildConfig;

public class RetrofitUtil {



    private RetrofitUtil() {

    }

    public static ApiService createApiService() {

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
                .baseUrl("https://edan.anka.pe/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                /*.callbackExecutor()*/
                .build()
                .create(ApiService.class);
    }
}
