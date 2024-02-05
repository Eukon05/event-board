package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.event.application.port.out.CheckUserInvitedOutPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.event.application.service.EventUnitTestUtils.*;

class ManageEventAttendanceUnitTests {
    private final SaveEventPort saveEventPort = Mockito.mock(SaveEventPort.class);
    private final GetEventPort getEventPort = Mockito.mock(GetEventPort.class);
    private final CheckUserInvitedOutPort checkUserInvitedOutPort = Mockito.mock(CheckUserInvitedOutPort.class);
    private final ManageEventAttendanceUseCase manageEventAttendanceUseCase = new ManageEventAttendanceUseCase(checkUserInvitedOutPort, getEventPort, saveEventPort);

    @Test
    void should_attend_event() {
        Event event = createTestPublicEvent();
        gettingEventWillReturn(getEventPort, event);

        manageEventAttendanceUseCase.attend(userID, event.getId());
        verify(getEventPort).getById(1L);
        verify(saveEventPort).saveEvent(event);

        assertThat(event.getGuestIDs(), contains(userID));
    }

    @Test
    void should_not_attend_private_event() {
        Event event = createTestPublicEvent();
        event.setType(EventType.PRIVATE);

        gettingEventWillReturn(getEventPort, event);
        checkingInvitedWillReturn(checkUserInvitedOutPort, event, false);

        assertEquals(Result.EVENT_PRIVATE, manageEventAttendanceUseCase.attend(userID, event.getId()));
    }

    @Test
    void should_attend_invited_event() {
        Event event = createTestPublicEvent();
        event.setType(EventType.PRIVATE);

        checkingInvitedWillReturn(checkUserInvitedOutPort, event, true);
        gettingEventWillReturn(getEventPort, event);

        assertEquals(Result.SUCCESS, manageEventAttendanceUseCase.attend(userID, event.getId()));
    }

    @Test
    void should_unattend_event() {
        Event event = createTestPublicEvent();
        gettingEventWillReturn(getEventPort, event);

        event.getGuestIDs().add(userID);

        manageEventAttendanceUseCase.unattend(userID, event.getId());

        verify(getEventPort).getById(1L);
        verify(saveEventPort).saveEvent(event);

        assertThat(event.getGuestIDs(), not(contains(userID)));
    }

    @Test
    void should_not_unattend_event() {
        Event event = createTestPublicEvent();
        gettingEventWillReturn(getEventPort, event);

        Result result = manageEventAttendanceUseCase.unattend(userID, event.getId());

        assertEquals(Result.NOT_ATTENDEE, result);
        verify(getEventPort).getById(1L);
    }
}
