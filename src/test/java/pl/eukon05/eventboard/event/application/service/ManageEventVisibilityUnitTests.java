package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.event.application.service.UnitTestUtils.*;

class ManageEventVisibilityUnitTests {
    private final SaveEventPort saveEventPort = Mockito.mock(SaveEventPort.class);
    private final GetEventPort getEventPort = Mockito.mock(GetEventPort.class);
    private final ManageEventVisibilityUseCase manageEventVisibilityUseCase = new ManageEventVisibilityUseCase(saveEventPort, getEventPort);

    @Test
    void should_publish_event() {
        Event event = createTestPublicEvent();
        event.setOrganizerID(userID);
        event.setType(EventType.PRIVATE);
        gettingEventWillReturn(getEventPort, event);

        Result result = manageEventVisibilityUseCase.publish(userID, 1L);

        assertEquals(Result.SUCCESS, result);
        verify(getEventPort).getById(1L);
        verify(saveEventPort).saveEvent(event);
        assertEquals(EventType.PUBLIC, event.getType());
    }

    @Test
    void should_not_publish_not_owned_event() {
        Event event = createTestPublicEvent();
        gettingEventWillReturn(getEventPort, event);

        assertEquals(Result.NOT_ORGANIZER, manageEventVisibilityUseCase.publish(userID, 1L));
    }

    @Test
    void should_not_publish_public_event() {
        Event event = createTestPublicEvent();
        event.setOrganizerID(userID);
        gettingEventWillReturn(getEventPort, event);

        assertEquals(Result.EVENT_ALREADY_PUBLIC, manageEventVisibilityUseCase.publish(userID, 1L));
    }

    @Test
    void should_unpublish_event() {
        Event event = createTestPublicEvent();
        event.setOrganizerID(userID);
        event.setType(EventType.PUBLIC);
        gettingEventWillReturn(getEventPort, event);

        Result result = manageEventVisibilityUseCase.unpublish(userID, 1L);

        assertEquals(Result.SUCCESS, result);
        verify(getEventPort).getById(1L);
        verify(saveEventPort).saveEvent(event);
        assertEquals(EventType.PRIVATE, event.getType());
    }

    @Test
    void should_not_unpublish_private_event() {
        Event event = createTestPublicEvent();
        event.setOrganizerID(userID);
        event.setType(EventType.PRIVATE);
        gettingEventWillReturn(getEventPort, event);

        Result result = manageEventVisibilityUseCase.unpublish(userID, 1L);

        assertEquals(Result.EVENT_ALREADY_PRIVATE, result);
    }

    @Test
    void should_not_unpublish_not_owned_event() {
        Event event = createTestPublicEvent();
        gettingEventWillReturn(getEventPort, event);

        Result result = manageEventVisibilityUseCase.unpublish(userID, 1L);

        assertEquals(Result.NOT_ORGANIZER, result);
    }
}
