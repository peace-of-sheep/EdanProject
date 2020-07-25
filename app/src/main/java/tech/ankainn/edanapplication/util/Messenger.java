package tech.ankainn.edanapplication.util;

public class Messenger {
    private String message;
    private boolean handled = false;

    public Messenger(String message) {
        this.message = message;
    }

    public String getMessageIfNotHandled() {
        if(handled) {
            return null;
        }
        handled = true;
        return message;
    }
}
