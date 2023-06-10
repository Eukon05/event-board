package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.event.application.service.UnitTestUtils.*;

class AttendEventUnitTests {
    private final SaveEventPort saveEventPort = Mockito.mock(SaveEventPort.class);
    private final GetEventPort getEventPort = Mockito.mock(GetEventPort.class);
    private final AttendEventUseCase attendEventUseCase = new AttendEventUseCase(getEventPort, saveEventPort);

    @Test
    void should_attend_event() {
        Event event = createTestPublicEvent();
        gettingEventWillReturn(getEventPort, event);

        attendEventUseCase.execute(userID, event.getId());
        verify(getEventPort).getEventById(1L);
        verify(saveEventPort).saveEvent(event);

        assertThat(event.getGuestIDs(), contains(userID));
    }

    @Test
    void should_not_attend_private_event() {
        Event event = createTestPublicEvent();
        event.setType(EventType.PRIVATE);
        gettingEventWillReturn(getEventPort, event);

        assertFalse(attendEventUseCase.execute(userID, event.getId()));
    }

    @Test
    void should_attend_invited_event() {
        Event event = createTestPublicEvent();
        event.setType(EventType.PRIVATE);
        event.getInviteeIDs().add(userID);
        gettingEventWillReturn(getEventPort, event);

        assertTrue(attendEventUseCase.execute(userID, event.getId()));
    }
}
