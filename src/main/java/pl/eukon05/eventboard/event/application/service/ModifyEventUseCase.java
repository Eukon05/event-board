package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.in.command.ModifyEventCommand;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class ModifyEventUseCase {

    private final SaveEventPort saveEventPort;
    private final GetEventPort getEventPort;

    Result modify(String userID, long id, ModifyEventCommand command) {
        Optional<Event> eventOptional = getEventPort.getById(id);
        if (eventOptional.isEmpty()) return Result.EVENT_NOT_FOUND;

        Event event = eventOptional.get();

        if (!event.getOrganizerID().equals(userID)) return Result.NOT_ORGANIZER;

        command.name().ifPresent(event::setName);
        command.description().ifPresent(event::setDescription);
        command.location().ifPresent(location -> {
            location.country().ifPresent(event.getLocation()::setCountry);
            location.city().ifPresent(event.getLocation()::setCity);
            location.street().ifPresent(event.getLocation()::setStreet);
            location.apartment().ifPresent(event.getLocation()::setApartment);
            location.postalCode().ifPresent(event.getLocation()::setPostalCode);
        });

        saveEventPort.saveEvent(event);
        return Result.SUCCESS;
    }

}
