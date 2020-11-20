package tech.ankainn.edanapplication.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tech.ankainn.edanapplication.model.api.ApiResponse;
import tech.ankainn.edanapplication.model.api.ResponseWrapper;
import tech.ankainn.edanapplication.model.api.formtwo.DataResponse;
import tech.ankainn.edanapplication.model.api.auth.AuthCredentials;
import tech.ankainn.edanapplication.model.api.auth.AuthResponse;
import tech.ankainn.edanapplication.model.api.formtwo.FormTwoRemote;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.ubigeo.DeptEntity;
import tech.ankainn.edanapplication.model.app.ubigeo.UbigeoLocation;

public interface GaldosService {

    @POST("login")
    Call<AuthResponse> postLogin(@Body AuthCredentials authCredentials);

    @POST("api/form2A-cab")
    Call<ApiResponse<DataResponse>> postFormTwo(@Body FormTwoRemote formTwoRemote);

    @POST("api/form2A-cab")
    Call<ApiResponse<DataResponse>> postFormTwo(@Body FormTwoData formTwoData);

    @GET("api/list-ubigeo-user/{ubigeo}")
    Call<ResponseWrapper<List<DeptEntity>>> getUbigeos(@Path("ubigeo") String ubigeo);

    @GET("api/list-dptos")
    Call<ResponseWrapper<List<UbigeoLocation>>> getDptos();

    @GET("api/list-provincias/{dpto_code}")
    Call<ResponseWrapper<List<UbigeoLocation>>> getProvinces(@Path("dpto_code") String codeDpto);

    @GET("api/list-distritos/{dpto_code}/{prov_code}")
    Call<ResponseWrapper<List<UbigeoLocation>>> getDistritos(@Path("dpto_code") String codeDpto, @Path("prov_code") String codeProv);
}
