package pl.eukon05.eventboard.invite.adapter.in;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.invite.application.port.in.CheckUserInvitedInPort;
import pl.eukon05.eventboard.invite.application.service.InviteInAdapterFacade;

@Adapter
@RequiredArgsConstructor
class InviteEventInAdapter implements CheckUserInvitedInPort {
    private final InviteInAdapterFacade facade;

    @Override
    public boolean checkUserInvited(String userId, long eventId) {
        return facade.checkUserInvited(userId, eventId);
    }
}
