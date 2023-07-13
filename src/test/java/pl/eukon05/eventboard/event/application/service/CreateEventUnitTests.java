package pl.eukon05.eventboard.event.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.event.application.port.in.command.CreateEventCommand;
import pl.eukon05.eventboard.event.application.port.in.command.CreateLocationCommand;
import pl.eukon05.eventboard.event.application.port.in.command.EventCommandMapper;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;
import pl.eukon05.eventboard.event.domain.Location;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.event.application.service.UnitTestUtils.userID;

class CreateEventUnitTests {
    private final SaveEventPort saveEventPort = Mockito.mock(SaveEventPort.class);
    private final EventCommandMapper mapper = Mockito.mock(EventCommandMapper.class);
    private final CreateEventUseCase createEventUseCase = new CreateEventUseCase(saveEventPort, mapper);
    private final CreateEventCommand command = new CreateEventCommand("test name",
            "desc",
            LocalDate.now(),
            LocalDate.now().plusDays(10),
            new CreateLocationCommand("country", "city", "street", "21", "30-000"));

    @Test
    void should_create_event() {
        Mockito.when(mapper.mapCreateCommandToDomain(Mockito.any(CreateEventCommand.class)))
                .thenReturn(new Event(null,
                        userID,
                        command.name(),
                        command.description(),
                        new Location(command.location().country(),
                                command.location().city(),
                                command.location().street(),
                                command.location().apartment(),
                                command.location().postalCode()),
                        EventType.PRIVATE,
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        Collections.emptySet(),
                        Collections.emptySet(),
                        Collections.emptySet()
                ));


        createEventUseCase.execute(userID, command);
        verify(saveEventPort).saveEvent(Mockito.any(Event.class));
    }
}
