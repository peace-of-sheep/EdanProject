package tech.ankainn.edanapplication.util;

public class SingleMsg {

    public final String emitter;

    private int message;

    private boolean handled = false;

    public SingleMsg(String emitter, int message) {
        this.emitter = emitter;
        this.message = message;
    }

    public int getMessageIfNotHandled() {
        if(handled) {
            return -1;
        }
        handled = true;
        return message;
    }
}
