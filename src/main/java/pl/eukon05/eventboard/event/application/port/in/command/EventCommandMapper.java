package pl.eukon05.eventboard.event.application.port.in.command;

import org.mapstruct.Mapper;
import pl.eukon05.eventboard.event.domain.Event;

@Mapper(componentModel = "spring")
public interface EventCommandMapper {

    Event mapCreateCommandToDomain(CreateEventCommand command);

}
