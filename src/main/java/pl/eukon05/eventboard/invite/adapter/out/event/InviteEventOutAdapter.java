package pl.eukon05.eventboard.invite.adapter.out.event;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.event.application.port.in.CheckEventGettableInPort;
import pl.eukon05.eventboard.invite.application.port.out.CheckEventGettableOutPort;

@Adapter
@RequiredArgsConstructor
class InviteEventOutAdapter implements CheckEventGettableOutPort {
    private final CheckEventGettableInPort checkEventGettableInPort;

    @Override
    public Result checkEventGettable(String userId, long eventId) {
        return checkEventGettableInPort.checkEventGettable(userId, eventId);
    }
}
