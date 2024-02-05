package pl.eukon05.eventboard.event.application.port.out;

public interface CheckUserAttendeeOutPort {
    boolean checkUserAttendee(String userId, long eventId);
}
