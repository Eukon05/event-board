package pl.eukon05.eventboard.event.application.port.in;

import pl.eukon05.eventboard.common.Result;

public interface CheckEventGettableInPort {
    Result checkEventGettable(String userId, long eventId);
}
