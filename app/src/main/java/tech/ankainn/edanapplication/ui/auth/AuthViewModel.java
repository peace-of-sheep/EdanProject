package tech.ankainn.edanapplication.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.global.Event;
import tech.ankainn.edanapplication.util.LiveEvent;

public class AuthViewModel extends ViewModel {

    private LiveEvent<Event> event = new LiveEvent<>();

    public LiveData<Event> getEvent() {
        return event;
    }

    public void sendEvent(Event eventName) {
        this.event.postValue(eventName);
    }
}
