package pl.eukon05.eventboard.invite.application.port.out;

public interface CheckUserInvitedOutPort {
    boolean checkUserInvited(String userId, long eventId);
}
