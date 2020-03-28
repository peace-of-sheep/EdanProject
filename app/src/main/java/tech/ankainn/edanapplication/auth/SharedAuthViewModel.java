package tech.ankainn.edanapplication.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import tech.ankainn.edanapplication.Event;
import tech.ankainn.edanapplication.util.LiveEvent;

public class SharedAuthViewModel extends ViewModel {

    private LiveEvent<Event> event = new LiveEvent<>();

    public LiveData<Event> getEvent() {
        return event;
    }

    public void sendEvent(Event eventName) {
        this.event.postValue(eventName);
    }
}
