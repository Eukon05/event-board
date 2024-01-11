package pl.eukon05.eventboard.invite.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.invite.application.port.out.CheckUserInvitedOutPort;
import pl.eukon05.eventboard.invite.application.port.out.DeleteInvitePort;

@UseCase
@RequiredArgsConstructor
class DeleteInviteUseCase {
    private final CheckUserInvitedOutPort checkUserInvitedOutPort;
    private final DeleteInvitePort deleteInvitePort;

    Result deleteInvite(String userId, long inviteId) {
        boolean isUserInvited = checkUserInvitedOutPort.checkUserInvited(userId, inviteId);

        if (!isUserInvited)
            return Result.INVITE_NOT_FOUND;

        deleteInvitePort.deleteInvite(userId, inviteId);
        return Result.SUCCESS;
    }
}
