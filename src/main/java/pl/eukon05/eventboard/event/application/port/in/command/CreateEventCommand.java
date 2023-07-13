package pl.eukon05.eventboard.event.application.port.in.command;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateEventCommand(@Size(min = 5, max = 100) @NotBlank String name,
                                 @Size(max = 1000) @NotNull String description,
                                 @NotNull @FutureOrPresent LocalDate startDate,
                                 @NotNull @FutureOrPresent LocalDate endDate,
                                 @NotNull CreateLocationCommand location) {
}
