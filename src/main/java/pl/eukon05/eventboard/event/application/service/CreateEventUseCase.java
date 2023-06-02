package pl.eukon05.eventboard.event.application.service;


import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

@UseCase
@RequiredArgsConstructor
class CreateEventUseCase {
    private final SaveEventPort saveEventPort;

    long execute(String userId, Event event) {
        event.setOrganizerID(userId);
        event.setType(EventType.PRIVATE);
        return saveEventPort.saveEvent(event);
    }
}
