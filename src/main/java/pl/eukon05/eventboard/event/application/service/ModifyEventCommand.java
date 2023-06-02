package pl.eukon05.eventboard.event.application.service;

import pl.eukon05.eventboard.event.domain.Location;

import java.util.Optional;

public record ModifyEventCommand(Optional<String> name, Optional<String> description, Optional<Location> location) {
}
