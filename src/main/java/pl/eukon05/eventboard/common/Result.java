package pl.eukon05.eventboard.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum Result {
    SUCCESS(HttpStatus.OK, "Operation completed successfully"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User with the given ID does not exist"),
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Event with the given ID does not exist"),
    USER_NOT_FRIEND(HttpStatus.BAD_REQUEST, "You are not friends with the given user"),
    USER_ALREADY_FRIEND(HttpStatus.BAD_REQUEST, "You are already friends with the given user"),
    EVENT_PRIVATE(HttpStatus.FORBIDDEN, "This event is private and you cannot access it"),
    ALREADY_ATTENDEE(HttpStatus.BAD_REQUEST, "You already attend this event"),
    ALREADY_INVITEE(HttpStatus.BAD_REQUEST, "The given user is already invited to the given event"),
    BEFRIEND_SELF(HttpStatus.BAD_REQUEST, "You cannot befriend yourself"),
    NOT_ORGANIZER(HttpStatus.FORBIDDEN, "You are not the organizer of the given event and thus cannot modify it"),
    EVENT_ALREADY_PUBLIC(HttpStatus.BAD_REQUEST, "This event is already public");


    private final HttpStatus status;
    private final String message;

}
