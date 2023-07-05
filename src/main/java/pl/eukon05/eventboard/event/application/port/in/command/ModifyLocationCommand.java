package pl.eukon05.eventboard.event.application.port.in.command;

import jakarta.validation.constraints.Size;
import pl.eukon05.eventboard.common.validator.NullOrNotBlank;

import java.util.Optional;

public record ModifyLocationCommand(Optional<@Size(max = 56) @NullOrNotBlank String> country,
                                    Optional<@Size(max = 58) @NullOrNotBlank String> city,
                                    Optional<@Size(max = 65) @NullOrNotBlank String> street,
                                    Optional<@Size(max = 10) @NullOrNotBlank String> apartment,
                                    Optional<@Size(min = 6, max = 6) @NullOrNotBlank String> postalCode) {
}
