package tech.ankainn.edanapplication.global;

import tech.ankainn.edanapplication.util.CallbackDispatcher;

public class Options extends CallbackDispatcher<Options.Callback> {

    private static Options instance;

    public static Options getInstance() {
        if(instance == null) {
            instance = new Options();
        }
        return instance;
    }

    private Options() {
    }

    void setOption(String emitter, int option) {
        dispatch(callback -> callback.onOption(emitter, option));
    }

    public interface Callback {
        void onOption(String emitter, int option);
    }
}
