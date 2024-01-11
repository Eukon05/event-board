package pl.eukon05.eventboard.invite.application.port.out;

import pl.eukon05.eventboard.invite.domain.Invite;

import java.util.List;
import java.util.Optional;

public interface GetInvitePort {
    Optional<Invite> getById(long id);

    List<Invite> getForUser(String userId);
}
