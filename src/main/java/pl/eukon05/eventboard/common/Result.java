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
    INVITE_NOT_FOUND(HttpStatus.NOT_FOUND, "Invite with the given ID does not exist"),
    USER_NOT_FRIEND(HttpStatus.BAD_REQUEST, "You are not friends with the given user"),
    USER_ALREADY_FRIEND(HttpStatus.BAD_REQUEST, "You are already friends with the given user"),
    EVENT_PRIVATE(HttpStatus.FORBIDDEN, "This event is private and you cannot access it"),
    ALREADY_ATTENDEE(HttpStatus.BAD_REQUEST, "You already attend this event"),
    NOT_ATTENDEE(HttpStatus.BAD_REQUEST, "You are not attending this event"),
    ALREADY_INVITEE(HttpStatus.BAD_REQUEST, "The given user is already invited to the given event"),
    NOT_INVITEE(HttpStatus.BAD_REQUEST, "You are not the recipient of this invite"),
    BEFRIEND_SELF(HttpStatus.BAD_REQUEST, "You cannot befriend yourself"),
    INVITE_SELF(HttpStatus.BAD_REQUEST, "You cannot invite yourself to an event"),
    INVITE_ORGANIZER(HttpStatus.BAD_REQUEST, "You cannot invite the organizer to their own event"),
    ATTEND_SELF_ORGANIZED_EVENT(HttpStatus.BAD_REQUEST, "You are the organizer of this event, no need to mark your attendance"),
    NOT_ORGANIZER(HttpStatus.FORBIDDEN, "You are not the organizer of the given event and thus cannot modify it"),
    EVENT_ALREADY_PUBLIC(HttpStatus.BAD_REQUEST, "This event is already public"),
    EVENT_ALREADY_PRIVATE(HttpStatus.BAD_REQUEST, "This event is already private"),
    FRIEND_REQUEST_ALREADY_SENT(HttpStatus.BAD_REQUEST, "You already sent a friend request to this user"),
    FRIEND_NOT_REQUESTED(HttpStatus.BAD_REQUEST, "This user has not sent you a friend request"),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "Validation of your input was not successful");


    private final HttpStatus status;
    private final String message;

}
