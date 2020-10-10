package tech.ankainn.edanapplication.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import tech.ankainn.edanapplication.model.api.ApiResponse;
import tech.ankainn.edanapplication.model.api.formtwo.DataResponse;
import tech.ankainn.edanapplication.model.api.auth.AuthCredentials;
import tech.ankainn.edanapplication.model.api.auth.AuthResponse;
import tech.ankainn.edanapplication.model.api.formtwo.FormTwoRemote;

public interface GaldosService {

    @POST("login")
    Call<AuthResponse> postLogin(@Body AuthCredentials authCredentials);

    @POST("api/form2A-cab")
    Call<ApiResponse<DataResponse>> postFormTwo(@Body FormTwoRemote formTwoRemote);
}
