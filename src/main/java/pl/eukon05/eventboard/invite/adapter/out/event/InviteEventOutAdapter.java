package pl.eukon05.eventboard.invite.adapter.out.event;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.event.application.port.in.CheckEventGettableInPort;
import pl.eukon05.eventboard.event.application.port.in.CheckUserAttendeeInPort;
import pl.eukon05.eventboard.event.application.port.in.CheckUserHostInPort;
import pl.eukon05.eventboard.invite.application.port.out.CheckEventGettableOutPort;
import pl.eukon05.eventboard.invite.application.port.out.CheckUserAttendeeOutPort;
import pl.eukon05.eventboard.invite.application.port.out.CheckUserHostOutPort;

@Adapter
@RequiredArgsConstructor
class InviteEventOutAdapter implements CheckEventGettableOutPort, CheckUserHostOutPort, CheckUserAttendeeOutPort {
    private final CheckEventGettableInPort checkEventGettableInPort;
    private final CheckUserHostInPort checkUserHostInPort;
    private final CheckUserAttendeeInPort checkUserAttendeeInPort;

    @Override
    public Result checkEventGettable(String userId, long eventId) {
        return checkEventGettableInPort.checkEventGettable(userId, eventId);
    }

    @Override
    public boolean checkUserHost(String userId, long eventId) {
        return checkUserHostInPort.checkUserHost(userId, eventId);
    }

    @Override
    public boolean checkUserAttendee(String userId, long eventId) {
        return checkUserAttendeeInPort.checkUserAttendee(userId, eventId);
    }
}
