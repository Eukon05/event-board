package pl.eukon05.eventboard.invite.application.port.in;

public interface CheckUserInvitedInPort {
    boolean checkUserInvited(String userId, long eventId);
}
