package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.event.application.port.out.CheckIfFriendsPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        checkingFriendsWillReturnTrue();

        assertTrue(inviteToEventUseCase.execute(userID, friendID, event.getId()));
        assertThat(event.getInviteeIDs(), contains(friendID));

        verify(checkIfFriendsPort).checkIfFriends(userID, friendID);
        verify(getEventPort).getEventById(event.getId());
        verify(saveEventPort).saveEvent(event);
    }

    @Test
    void should_invite_to_private_event() {
        Event event = createTestPublicEvent();

        event.setType(EventType.PRIVATE);
        event.setOrganizerID(userID);

        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));
        Mockito.when(checkIfFriendsPort.checkIfFriends(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        assertTrue(inviteToEventUseCase.execute(userID, friendID, event.getId()));
    }

    private void checkingFriendsWillReturnTrue() {
        Mockito.when(checkIfFriendsPort.checkIfFriends(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
    }
}
