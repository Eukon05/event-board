package pl.eukon05.eventboard.invite.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.invite.application.port.in.command.CreateInviteCommand;
import pl.eukon05.eventboard.invite.domain.Invite;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteFacade {
    private final CreateInviteUseCase createInviteUseCase;
    private final DeleteInviteUseCase deleteInviteUseCase;
    private final GetInvitesUseCase getInvitesUseCase;

    public ResultWrapper<String> createInvite(String userId, CreateInviteCommand command) {
        return ResultWrapper.wrap(createInviteUseCase.createInvite(userId, command));
    }

    public ResultWrapper<String> deleteInvite(String userId, long inviteId) {
        return ResultWrapper.wrap(deleteInviteUseCase.deleteInvite(userId, inviteId));
    }

    public ResultWrapper<List<Invite>> getInvites(String userId) {
        return ResultWrapper.<List<Invite>>builder().result(Result.SUCCESS).data(getInvitesUseCase.getInvites(userId)).build();
    }
}
