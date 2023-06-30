package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.event.application.port.out.CheckIfFriendsPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.event.application.service.UnitTestUtils.*;

class InviteToEventUnitTests {
    private final SaveEventPort saveEventPort = Mockito.mock(SaveEventPort.class);
    private final GetEventPort getEventPort = Mockito.mock(GetEventPort.class);
    private final CheckIfFriendsPort checkIfFriendsPort = Mockito.mock(CheckIfFriendsPort.class);
    private final InviteToEventUseCase inviteToEventUseCase = new InviteToEventUseCase(checkIfFriendsPort, getEventPort, saveEventPort);

    @Test
    void should_invite_to_public_event() {
        Event event = createTestPublicEvent();
        gettingEventWillReturn(getEventPort, event);
        checkingFriendsWillReturn(true);

        assertEquals(Result.SUCCESS, inviteToEventUseCase.execute(userID, friendID, event.getId()));
        assertThat(event.getInviteeIDs(), contains(friendID));

        verify(checkIfFriendsPort).checkIfFriends(userID, friendID);
        verify(getEventPort).getEventById(event.getId());
        verify(saveEventPort).saveEvent(event);
    }

    @Test
    void should_invite_to_private_hosted_event() {
        Event event = createTestPublicEvent();

        event.setType(EventType.PRIVATE);
        event.setOrganizerID(userID);

        gettingEventWillReturn(getEventPort, event);
        checkingFriendsWillReturn(true);

        assertEquals(Result.SUCCESS, inviteToEventUseCase.execute(userID, friendID, event.getId()));
    }

    @Test
    void should_invite_to_private_attended_event() {
        Event event = createTestPublicEvent();

        event.setType(EventType.PRIVATE);
        event.getGuestIDs().add(userID);

        gettingEventWillReturn(getEventPort, event);
        checkingFriendsWillReturn(true);

        assertEquals(Result.SUCCESS, inviteToEventUseCase.execute(userID, friendID, event.getId()));
    }

    @Test
    void should_not_invite_non_friend() {
        Event event = createTestPublicEvent();

        gettingEventWillReturn(getEventPort, event);
        checkingFriendsWillReturn(false);

        assertEquals(Result.USER_NOT_FRIEND, inviteToEventUseCase.execute(userID, friendID, event.getId()));
    }

    @Test
    void should_not_invite_to_private_event_when_self_not_invited() {
        Event event = createTestPublicEvent();

        event.setType(EventType.PRIVATE);

        gettingEventWillReturn(getEventPort, event);
        checkingFriendsWillReturn(true);

        assertEquals(Result.EVENT_PRIVATE, inviteToEventUseCase.execute(userID, friendID, event.getId()));
    }

    private void checkingFriendsWillReturn(boolean value) {
        Mockito.when(checkIfFriendsPort.checkIfFriends(Mockito.anyString(), Mockito.anyString())).thenReturn(value);
    }
}
