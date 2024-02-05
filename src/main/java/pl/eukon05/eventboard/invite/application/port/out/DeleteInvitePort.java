package pl.eukon05.eventboard.invite.application.port.out;

public interface DeleteInvitePort {
    void deleteInvite(String inviteeId, long eventId);
}
