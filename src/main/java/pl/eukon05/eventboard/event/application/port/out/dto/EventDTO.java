package pl.eukon05.eventboard.event.application.port.out.dto;

import pl.eukon05.eventboard.event.domain.EventType;

import java.time.LocalDate;

public record EventDTO(String organizerID, String name, String description, LocationDTO location, EventType type,
                       LocalDate startDate, LocalDate endDate, long guestCount, long likesCount) {
}
