package tech.ankainn.edanapplication.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseWrapper<T> {

    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("data")
    @Expose
    public T data = null;
    @SerializedName("token")
    @Expose
    public String token;
}
