package tech.ankainn.edanapplication.global;

public class Picker extends CallbackDispatcher<Picker.Callback> {

    private static Picker instance;

    public static Picker getInstance() {
        if(instance == null) {
            instance = new Picker();
        }
        return instance;
    }

    private Picker() {
    }

    void setValue(String emitter, String value) {
        dispatch(callback -> callback.setValue(emitter, value));
    }

    public interface Callback {
        void setValue(String emitter, String value);
    }
}
