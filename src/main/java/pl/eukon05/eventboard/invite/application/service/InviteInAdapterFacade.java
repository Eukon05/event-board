package pl.eukon05.eventboard.invite.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InviteInAdapterFacade {
    private final CheckUserInvitedUseCase checkUserInvitedUseCase;

    public boolean checkUserInvited(String userId, long eventId) {
        return checkUserInvitedUseCase.checkUserInvited(userId, eventId);
    }
}
