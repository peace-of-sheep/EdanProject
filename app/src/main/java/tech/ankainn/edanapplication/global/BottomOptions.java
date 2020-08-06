package tech.ankainn.edanapplication.global;

import tech.ankainn.edanapplication.util.CallbackDispatcher;

public class BottomOptions extends CallbackDispatcher<BottomOptions.Callback> {

    private static BottomOptions instance;

    public static BottomOptions getInstance() {
        if(instance == null) {
            instance = new BottomOptions();
        }
        return instance;
    }

    private BottomOptions() {
    }

    void setOption(String emitter, int option) {
        dispatch(callback -> callback.onOption(emitter, option));
    }

    public interface Callback {
        void onOption(String emitter, int option);
    }
}
