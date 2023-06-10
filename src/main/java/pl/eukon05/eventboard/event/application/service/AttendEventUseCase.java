package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class AttendEventUseCase {
    private final GetEventPort getEventPort;
    private final SaveEventPort saveEventPort;

    boolean execute(String userID, long eventID) {
        Optional<Event> eventOptional = getEventPort.getEventById(eventID);

        if (eventOptional.isEmpty()) return false;

        Event event = eventOptional.get();

        boolean result = event.attend(userID);

        if (result) saveEventPort.saveEvent(event);

        return result;
    }
}
