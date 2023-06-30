package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.Location;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.event.application.service.UnitTestUtils.*;

class ModifyEventUnitTests {
    private final SaveEventPort saveEventPort = Mockito.mock(SaveEventPort.class);
    private final GetEventPort getEventPort = Mockito.mock(GetEventPort.class);
    private final ModifyEventUseCase modifyEventUseCase = new ModifyEventUseCase(saveEventPort, getEventPort);
    private final ModifyEventCommand command = new ModifyEventCommand(Optional.of("NEWNAME"), Optional.of("NEW_DESC"), Optional.of(new Location("somecountry", "somecity", "somestr", "21e", "00-000")));

    @Test
    void should_modify_event() {
        Event event = createTestPublicEvent();
        event.setOrganizerID(userID);

        gettingEventWillReturn(getEventPort, event);

        modifyEventUseCase.execute(userID, 1L, command);
        verify(getEventPort).getEventById(1L);

        assertEquals(command.name().get(), event.getName());
        assertEquals(command.description().get(), event.getDescription());
        assertEquals(command.location().get(), event.getLocation());

        verify(saveEventPort).saveEvent(event);
    }

    @Test
    void should_not_modify_event() {
        Event event = createTestPublicEvent();
        gettingEventWillReturn(getEventPort, event);

        assertEquals(Result.NOT_ORGANIZER, modifyEventUseCase.execute(userID, 1L, command));
    }

}
