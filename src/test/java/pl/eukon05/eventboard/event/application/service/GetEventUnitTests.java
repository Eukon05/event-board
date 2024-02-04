package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.dto.EventDTOMapper;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.event.application.service.UnitTestUtils.*;

class GetEventUnitTests {

    private final GetEventPort getEventPort = Mockito.mock(GetEventPort.class);
    private final EventDTOMapper mapper = Mockito.mock(EventDTOMapper.class);
    private final GetEventUseCase getEventUseCase = new GetEventUseCase(getEventPort, mapper);

    @Test
    void should_get_public_event() {
        Event event = createTestPublicEvent();
        gettingEventWillReturn(getEventPort, event);

        ResultWrapper<?> wrapper = getEventUseCase.getById(userID, event.getId());
        assertEquals(Result.SUCCESS, wrapper.getResult());
        verify(mapper).mapDomainToDTO(event);
    }

    @Test
    void should_not_get_private_event() {
        Event event = createTestPublicEvent();
        event.setType(EventType.PRIVATE);
        gettingEventWillReturn(getEventPort, event);

        ResultWrapper<?> wrapper = getEventUseCase.getById(userID, event.getId());
        assertEquals(Result.EVENT_PRIVATE, wrapper.getResult());
        assertEquals(Result.EVENT_PRIVATE.getMessage(), wrapper.getMessage());
    }

    @Test
    void should_get_private_invited_event() {
        Event event = createTestPublicEvent();
        event.setType(EventType.PRIVATE);
        event.getInviteeIDs().add(userID);
        gettingEventWillReturn(getEventPort, event);

        ResultWrapper<?> wrapper = getEventUseCase.getById(userID, event.getId());
        assertEquals(Result.SUCCESS, wrapper.getResult());
    }

    @Test
    void should_get_private_attended_event() {
        Event event = createTestPublicEvent();
        event.setType(EventType.PRIVATE);
        event.getGuestIDs().add(userID);
        gettingEventWillReturn(getEventPort, event);

        ResultWrapper<?> wrapper = getEventUseCase.getById(userID, event.getId());
        assertEquals(Result.SUCCESS, wrapper.getResult());
    }

    @Test
    void should_get_private_organized_event() {
        Event event = createTestPublicEvent();
        event.setOrganizerID(userID);
        event.setType(EventType.PRIVATE);
        gettingEventWillReturn(getEventPort, event);

        ResultWrapper<?> wrapper = getEventUseCase.getById(userID, event.getId());
        assertEquals(Result.SUCCESS, wrapper.getResult());
    }

}
