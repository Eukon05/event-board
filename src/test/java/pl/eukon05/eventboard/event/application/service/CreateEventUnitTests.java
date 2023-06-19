package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.event.application.service.UnitTestUtils.createTestPublicEvent;
import static pl.eukon05.eventboard.event.application.service.UnitTestUtils.userID;

class CreateEventUnitTests {
    private final SaveEventPort saveEventPort = Mockito.mock(SaveEventPort.class);
    private final CreateEventUseCase createEventUseCase = new CreateEventUseCase(saveEventPort);

    @Test
    void should_create_event() {
        Event event = createTestPublicEvent();

        createEventUseCase.execute(userID, event);

        verify(saveEventPort).saveEvent(event);
        assertEquals(EventType.PRIVATE, event.getType());
        assertEquals(userID, event.getOrganizerID());
    }
}
