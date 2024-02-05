package pl.eukon05.eventboard.event.application.port.out;

public interface CheckUserInvitedOutPort {
    boolean checkUserInvited(String userId, long eventId);
}
