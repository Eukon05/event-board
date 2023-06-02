package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.out.DeleteEventPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.domain.Event;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class DeleteEventUseCase {
    private final GetEventPort getEventPort;
    private final DeleteEventPort deleteEventPort;

    public boolean execute(String userID, long eventID) {
        Optional<Event> eventOptional = getEventPort.getEventById(eventID);
        if (eventOptional.isEmpty())
            return false;

        Event event = eventOptional.get();

        if (!event.getOrganizerID().equals(userID))
            return false;

        deleteEventPort.deleteEvent(eventID);
        return true;
    }

}
