package pl.eukon05.eventboard.event.application.port.out;

import pl.eukon05.eventboard.event.domain.Event;

public interface SaveEventPort {

    void saveEvent(Event event);

}
