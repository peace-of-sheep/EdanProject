package tech.ankainn.edanapplication.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tech.ankainn.edanapplication.model.api.ReniecData;

public interface ReniecService {

    @GET("{dniNumber}")
    Call<ReniecData> getPersonByDniNumber(@Path("dniNumber") String dniNumber);
}
