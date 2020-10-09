package tech.ankainn.edanapplication.model.api.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthCredentials {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("clave")
    @Expose
    private String clave;

    public AuthCredentials() {
    }

    public AuthCredentials(String username, String clave) {
        this.username = username;
        this.clave = clave;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
