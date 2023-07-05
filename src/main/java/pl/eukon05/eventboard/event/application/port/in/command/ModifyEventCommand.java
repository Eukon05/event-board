package pl.eukon05.eventboard.event.application.port.in.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import pl.eukon05.eventboard.common.validator.NullOrNotBlank;

import java.time.LocalDate;
import java.util.Optional;

public record ModifyEventCommand(Optional<@Size(min = 5, max = 100) @NullOrNotBlank String> name,
                                 Optional<@Size(max = 1000) String> description,
                                 Optional<@Valid ModifyLocationCommand> location,
                                 Optional<@FutureOrPresent LocalDate> startDate,
                                 Optional<@FutureOrPresent LocalDate> endDate) {
}
