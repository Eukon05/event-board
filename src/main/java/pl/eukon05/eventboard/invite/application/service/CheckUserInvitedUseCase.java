package pl.eukon05.eventboard.invite.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.invite.application.port.out.CheckUserInvitedOutPort;

@UseCase
@RequiredArgsConstructor
class CheckUserInvitedUseCase {
    private final CheckUserInvitedOutPort checkUserInvitedOutPort;

    boolean checkUserInvited(String userId, long eventId) {
        return checkUserInvitedOutPort.checkUserInvited(userId, eventId);
    }
}
