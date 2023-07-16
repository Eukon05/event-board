package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.event.application.port.in.command.ModifyEventCommand;
import pl.eukon05.eventboard.event.application.port.in.command.ModifyLocationCommand;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.event.application.service.UnitTestUtils.*;

class ModifyEventUnitTests {
    private final SaveEventPort saveEventPort = Mockito.mock(SaveEventPort.class);
    private final GetEventPort getEventPort = Mockito.mock(GetEventPort.class);
    private final ModifyEventUseCase modifyEventUseCase = new ModifyEventUseCase(saveEventPort, getEventPort);
    private final ModifyEventCommand command = new ModifyEventCommand(
            Optional.of("NEWNAME"),
            Optional.of("NEW_DESC"),
            Optional.of(new ModifyLocationCommand(Optional.of("somecountry"), Optional.of("somecity"), Optional.of("somestr"), Optional.of("21e"), Optional.of("00-000"))),
            Optional.of(LocalDate.now()),
            Optional.of(LocalDate.now().plusDays(10)));

    @Test
    void should_modify_event() {
        Event event = createTestPublicEvent();
        event.setOrganizerID(userID);

        gettingEventWillReturn(getEventPort, event);

        modifyEventUseCase.modify(userID, 1L, command);
        verify(getEventPort).getById(1L);

        assertEquals(command.name().get(), event.getName());
        assertEquals(command.description().get(), event.getDescription());
        //assertEquals(command.location().get(), event.getLocation());

        verify(saveEventPort).saveEvent(event);
    }

    @Test
    void should_not_modify_event() {
        Event event = createTestPublicEvent();
        gettingEventWillReturn(getEventPort, event);

        assertEquals(Result.NOT_ORGANIZER, modifyEventUseCase.modify(userID, 1L, command));
    }

}
