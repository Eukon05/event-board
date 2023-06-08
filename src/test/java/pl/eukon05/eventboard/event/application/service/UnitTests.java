package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.event.application.port.in.GetUserFriendlistPort;
import pl.eukon05.eventboard.event.application.port.out.DeleteEventPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;
import pl.eukon05.eventboard.event.domain.Location;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class UnitTests {
    private final SaveEventPort saveEventPort = Mockito.mock(SaveEventPort.class);
    private final GetEventPort getEventPort = Mockito.mock(GetEventPort.class);
    private final GetUserFriendlistPort getUserFriendlistPort = Mockito.mock(GetUserFriendlistPort.class);
    private final DeleteEventPort deleteEventPort = Mockito.mock(DeleteEventPort.class);
    private final CreateEventUseCase createEventUseCase = new CreateEventUseCase(saveEventPort);
    private final PublishEventUseCase publishEventUseCase = new PublishEventUseCase(saveEventPort, getEventPort);
    private final ModifyEventUseCase modifyEventUseCase = new ModifyEventUseCase(saveEventPort, getEventPort);
    private final DeleteEventUseCase deleteEventUseCase = new DeleteEventUseCase(getEventPort, deleteEventPort);
    private final AttendEventUseCase attendEventUseCase = new AttendEventUseCase(getEventPort, saveEventPort);
    private final InviteToEventUseCase inviteToEventUseCase = new InviteToEventUseCase(getUserFriendlistPort, getEventPort, saveEventPort);
    private final String userID = "someid";

    private Event createTestEvent() {
        return new Event(1L, "", "name", "desc", new Location("sas", "sas", "sas"), EventType.PUBLIC, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    @Test
    void should_create_event() {
        Event event = createTestEvent();

        createEventUseCase.execute(userID, event);

        verify(saveEventPort).saveEvent(event);
        assertEquals(EventType.PRIVATE, event.getType());
        assertEquals(userID, event.getOrganizerID());
    }

    @Test
    void should_publish_event() {
        Event event = createTestEvent();
        event.setOrganizerID(userID);
        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));

        publishEventUseCase.execute(userID, 1L);
        verify(getEventPort).getEventById(1L);
        assertEquals(EventType.PUBLIC, event.getType());
        verify(saveEventPort).saveEvent(event);
    }

    @Test
    void should_not_publish_event() {
        Event event = createTestEvent();
        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));

        assertFalse(publishEventUseCase.execute(userID, 1L));
    }

    @Test
    void should_modify_event() {
        Event event = createTestEvent();
        event.setOrganizerID(userID);
        ModifyEventCommand command = new ModifyEventCommand(Optional.of("NEWNAME"), Optional.of("NEW_DESC"), Optional.of(new Location("somestr", "21e", "00-000")));

        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));

        modifyEventUseCase.execute(userID, 1L, command);
        verify(getEventPort).getEventById(1L);

        assertEquals(command.name().get(), event.getName());
        assertEquals(command.description().get(), event.getDescription());
        assertEquals(command.location().get(), event.getLocation());

        verify(saveEventPort).saveEvent(event);
    }

    @Test
    void should_not_modify_event() {
        Event event = createTestEvent();

        ModifyEventCommand command = new ModifyEventCommand(Optional.of("NEWNAME"), Optional.of("NEW_DESC"), Optional.of(new Location("somestr", "21e", "00-000")));

        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));

        assertFalse(modifyEventUseCase.execute(userID, 1L, command));
    }

    @Test
    void should_delete_event() {
        Event event = createTestEvent();
        event.setOrganizerID(userID);

        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));

        deleteEventUseCase.execute(userID, 1L);
        verify(getEventPort).getEventById(1L);
        verify(deleteEventPort).deleteEvent(event.getId());
    }

    @Test
    void should_not_delete_event() {
        Event event = createTestEvent();
        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));

        assertFalse(deleteEventUseCase.execute(userID, 1L));
    }

    @Test
    void should_attend_event() {
        Event event = createTestEvent();
        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));

        attendEventUseCase.execute(userID, event.getId());
        verify(getEventPort).getEventById(1L);
        verify(saveEventPort).saveEvent(event);

        assertTrue(event.getGuestIDs().contains(userID));
    }

    @Test
    void should_not_attend_private_event() {
        Event event = createTestEvent();
        event.setType(EventType.PRIVATE);
        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));

        assertFalse(attendEventUseCase.execute(userID, event.getId()));
    }

    @Test
    void should_attend_invited_event() {
        Event event = createTestEvent();
        event.setType(EventType.PRIVATE);
        event.getInviteeIDs().add(userID);
        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));

        assertTrue(attendEventUseCase.execute(userID, event.getId()));
    }

    @Test
    void should_invite_to_public_event() {
        Event event = createTestEvent();
        String friendID = "somerandomid";
        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));
        Mockito.when(getUserFriendlistPort.getUserFriendlist(Mockito.anyString())).thenReturn(List.of(friendID));

        assertTrue(inviteToEventUseCase.execute(userID, friendID, event.getId()));
        assertTrue(event.getInviteeIDs().contains(friendID));
        verify(getUserFriendlistPort).getUserFriendlist(userID);
        verify(getEventPort).getEventById(event.getId());
        verify(saveEventPort).saveEvent(event);
    }

    @Test
    void should_invite_to_private_event() {
        Event event = createTestEvent();

        event.setType(EventType.PRIVATE);
        event.setOrganizerID(userID);

        String friendID = "somerandomid";
        Mockito.when(getEventPort.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));
        Mockito.when(getUserFriendlistPort.getUserFriendlist(Mockito.anyString())).thenReturn(List.of(friendID));

        assertTrue(inviteToEventUseCase.execute(userID, friendID, event.getId()));

        event.setOrganizerID("");
        event.getInviteeIDs().remove(friendID);
        event.getGuestIDs().add(userID);

        assertTrue(inviteToEventUseCase.execute(userID, friendID, event.getId()));
    }
}
