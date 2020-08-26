package tech.ankainn.edanapplication.api;

public class RestResponse<T> {

    // Codigos de error
    public static final String ERROR_PARSEO_JSON = "1000";
    public static final String NO_CREADO = "1001";
    public static final String CAMPO_NO_VALIDO = "1002";

    // Codigos de exitos
    public static final String CREADO_EXITOSAMENTE = "2000";
    public static final String LEIDO_EXITOSAMENTE = "2001";

    private String mensaje;

    private String detalle;

    private String codigo;

    private String masInformacion;

    private Boolean hayMasPaginas;

    private T data;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the masInformacion
     */
    public String getMasInformacion() {
        return masInformacion;
    }

    /**
     * @param masInformacion the masInformacion to set
     */
    public void setMasInformacion(String masInformacion) {
        this.masInformacion = masInformacion;
    }

    /**
     * @return the hayMasPaginas
     */
    public Boolean getHayMasPaginas() {
        return hayMasPaginas;
    }

    /**
     * @param hayMasPaginas the hayMasPaginas to set
     */
    public void setHayMasPaginas(Boolean hayMasPaginas) {
        this.hayMasPaginas = hayMasPaginas;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
