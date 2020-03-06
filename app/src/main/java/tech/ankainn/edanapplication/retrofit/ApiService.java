package tech.ankainn.edanapplication.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import tech.ankainn.edanapplication.model.AuthCredentials;
import tech.ankainn.edanapplication.util.RestResponse;

public interface ApiService {
    @POST("usuarios/auth")
    Call<RestResponse<String>> postSignInAuth(@Body AuthCredentials authCredentials);
}
