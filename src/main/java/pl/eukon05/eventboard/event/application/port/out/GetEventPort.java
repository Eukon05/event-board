package pl.eukon05.eventboard.event.application.port.out;

import pl.eukon05.eventboard.event.domain.Event;

import java.util.Optional;

public interface GetEventPort {

    Optional<Event> getEventById(long eventID);

}
