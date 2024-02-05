package pl.eukon05.eventboard.event.adapter.in;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.event.application.port.in.CheckEventGettableInPort;
import pl.eukon05.eventboard.event.application.port.in.CheckUserAttendeeInPort;
import pl.eukon05.eventboard.event.application.port.in.CheckUserHostInPort;
import pl.eukon05.eventboard.event.application.service.EventInAdapterFacade;

@Adapter
@RequiredArgsConstructor
class EventInviteInAdapter implements CheckEventGettableInPort, CheckUserHostInPort, CheckUserAttendeeInPort {
    private final EventInAdapterFacade facade;

    @Override
    public Result checkEventGettable(String userId, long eventId) {
        return facade.checkEventGettable(userId, eventId);
    }

    @Override
    public boolean checkUserHost(String userId, long eventId) {
        return facade.checkUserHost(userId, eventId);
    }

    @Override
    public boolean checkUserAttendee(String userId, long eventId) {
        return facade.checkUserAttendee(userId, eventId);
    }
}
