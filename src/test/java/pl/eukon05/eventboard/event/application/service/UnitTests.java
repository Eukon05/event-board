package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.event.application.port.out.DeleteEventPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;
import pl.eukon05.eventboard.event.domain.Location;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;

class UnitTests {
    private final SaveEventPort saveEventPort = Mockito.mock(SaveEventPort.class);
    private final GetEventPort getEventPort = Mockito.mock(GetEventPort.class);
    private final DeleteEventPort deleteEventPort = Mockito.mock(DeleteEventPort.class);
    private final CreateEventUseCase createEventUseCase = new CreateEventUseCase(saveEventPort);
    private final PublishEventUseCase publishEventUseCase = new PublishEventUseCase(saveEventPort, getEventPort);
    private final ModifyEventUseCase modifyEventUseCase = new ModifyEventUseCase(saveEventPort, getEventPort);
    private final DeleteEventUseCase deleteEventUseCase = new DeleteEventUseCase(getEventPort, deleteEventPort);
    private final String userID = "someid";

    private Event createTestEvent() {
        return new Event(1L, "", "name", "desc", new Location("sas", "sas", "sas"), null, null, null, null);
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
}
