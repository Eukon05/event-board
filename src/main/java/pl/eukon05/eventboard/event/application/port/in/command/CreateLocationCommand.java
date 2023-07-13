package pl.eukon05.eventboard.event.application.port.in.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateLocationCommand(@Size(max = 56) @NotBlank String country,
                                    @Size(max = 58) @NotBlank String city,
                                    @Size(max = 65) @NotBlank String street,
                                    @Size(max = 10) @NotNull String apartment,
                                    @Size(min = 6, max = 6) @NotBlank String postalCode) {
}
