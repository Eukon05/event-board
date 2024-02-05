package pl.eukon05.eventboard.event.application.port.in;

public interface CheckUserAttendeeInPort {
    boolean checkUserAttendee(String userId, long eventId);
}
