package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.event.application.port.in.command.CreateEventCommand;
import pl.eukon05.eventboard.event.application.port.in.command.ModifyEventCommand;
import pl.eukon05.eventboard.event.domain.Event;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventFacade {
    private final AttendEventUseCase attendEventUseCase;
    private final CreateEventUseCase createEventUseCase;
    private final DeleteEventUseCase deleteEventUseCase;
    private final InviteToEventUseCase inviteToEventUseCase;
    private final ModifyEventUseCase modifyEventUseCase;
    private final PublishEventUseCase publishEventUseCase;
    private final SearchForEventUseCase searchForEventUseCase;

    public Result createEvent(String userID, CreateEventCommand command) {
        return createEventUseCase.execute(userID, command);
    }

    public Result publishEvent(String userID, long eventID) {
        return publishEventUseCase.execute(userID, eventID);
    }

    public Result attendEvent(String userID, long eventID) {
        return attendEventUseCase.execute(userID, eventID);
    }

    public Result inviteToEvent(String userID, String friendID, long eventID) {
        return inviteToEventUseCase.execute(userID, friendID, eventID);
    }

    public Result deleteEvent(String userID, long eventID) {
        return deleteEventUseCase.execute(userID, eventID);
    }

    //todo THIS CANNOT RETURN THE DOMAIN OBJECT, PLEASE CREATE A DTO TO HOLD THE EVENT DATA
    public Page<Event> searchForEvent(Map<String, String> parameters, Pageable pageable) {
        return searchForEventUseCase.execute(parameters, pageable);
    }

    public Result modifyEvent(String userID, long eventID, ModifyEventCommand command) {
        return modifyEventUseCase.execute(userID, eventID, command);
    }

}
