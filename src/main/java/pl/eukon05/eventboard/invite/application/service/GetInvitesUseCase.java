package pl.eukon05.eventboard.invite.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.invite.application.port.out.GetInvitePort;
import pl.eukon05.eventboard.invite.domain.Invite;

import java.util.List;

@UseCase
@RequiredArgsConstructor
class GetInvitesUseCase {
    private final GetInvitePort getInvitePort;

    List<Invite> getInvites(String userId) {
        return getInvitePort.getForUser(userId);
    }
}
