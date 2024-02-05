package pl.eukon05.eventboard.invite.application.port.out;

public interface CheckUserAttendeeOutPort {
    boolean checkUserAttendee(String userId, long eventId);
}
