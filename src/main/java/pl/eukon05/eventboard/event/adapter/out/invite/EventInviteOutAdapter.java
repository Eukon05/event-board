package pl.eukon05.eventboard.event.adapter.out.invite;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.event.application.port.out.CheckUserInvitedOutPort;
import pl.eukon05.eventboard.invite.application.port.in.CheckUserInvitedInPort;

@Adapter
@RequiredArgsConstructor
class EventInviteOutAdapter implements CheckUserInvitedOutPort {
    private final CheckUserInvitedInPort checkUserInvitedInPort;

    @Override
    public boolean checkUserInvited(String userId, long eventId) {
        return checkUserInvitedInPort.checkUserInvited(userId, eventId);
    }
}
