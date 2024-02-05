package pl.eukon05.eventboard.invite.application.port.out;

import pl.eukon05.eventboard.invite.domain.Invite;

public interface SaveInvitePort {
    void saveInvite(Invite invite);
}
