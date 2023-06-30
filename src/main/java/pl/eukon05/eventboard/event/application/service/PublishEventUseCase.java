package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class PublishEventUseCase {

    private final SaveEventPort saveEventPort;
    private final GetEventPort getEventPort;

    public Result execute(String userID, long id) {
        Optional<Event> eventOptional = getEventPort.getEventById(id);
        if (eventOptional.isEmpty())
            return Result.EVENT_NOT_FOUND;

        Event event = eventOptional.get();

        if (!event.getOrganizerID().equals(userID))
            return Result.NOT_ORGANIZER;

        event.setType(EventType.PUBLIC);
        saveEventPort.saveEvent(event);

        return Result.SUCCESS;
    }

}
