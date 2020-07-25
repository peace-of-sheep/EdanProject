package tech.ankainn.edanapplication.util;

public class Metadata<T> {
    public final T data;
    public final boolean loading;

    public Metadata(T data) {
        this.data = data;
        loading = false;
    }

    public Metadata(T data, boolean loading) {
        this.data = data;
        this.loading = loading;
    }
}
