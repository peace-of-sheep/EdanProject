package tech.ankainn.edanapplication.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EdanApiService {

    /*@POST("usuarios/auth")
    Call<RestResponse<String>> postSignInAuth(@Body AuthCredentials authCredentials);*/

    /*@POST("api/form2A-cab")
    Call<ApiResponse<DataResponse>> postForm2a(@Body ApiFormTwo formtwo);*/

    /*@GET("api/form2a-cab")
    Call<ApiListResponse> getList();*/

    @GET("maeDepartamentoes")
    Call<Void> getDepartamentos();
}
