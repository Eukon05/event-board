package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.event.application.port.out.DeleteEventPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.domain.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.event.application.service.EventUnitTestUtils.*;

class DeleteEventUnitTests {
    private final GetEventPort getEventPort = Mockito.mock(GetEventPort.class);
    private final DeleteEventPort deleteEventPort = Mockito.mock(DeleteEventPort.class);
    private final DeleteEventUseCase deleteEventUseCase = new DeleteEventUseCase(getEventPort, deleteEventPort);

    @Test
    void should_delete_event() {
        Event event = createTestPublicEvent();
        event.setOrganizerID(userID);

        gettingEventWillReturn(getEventPort, event);

        deleteEventUseCase.delete(userID, 1L);
        verify(getEventPort).getById(1L);
        verify(deleteEventPort).deleteEvent(event.getId());
    }

    @Test
    void should_not_delete_event() {
        Event event = createTestPublicEvent();
        gettingEventWillReturn(getEventPort, event);

        assertEquals(Result.NOT_ORGANIZER, deleteEventUseCase.delete(userID, 1L));
    }
}
