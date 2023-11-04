package pl.eukon05.eventboard.event.application.service;


import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.in.command.CreateEventCommand;
import pl.eukon05.eventboard.event.application.port.in.command.EventCommandMapper;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

@UseCase
@RequiredArgsConstructor
class CreateEventUseCase {
    private final SaveEventPort saveEventPort;
    private final EventCommandMapper mapper;

    long create(String userId, CreateEventCommand command) {
        Event event = mapper.mapCreateCommandToDomain(command);
        event.setType(EventType.PRIVATE);
        event.setOrganizerID(userId);

        return saveEventPort.saveEvent(event);
    }
}
