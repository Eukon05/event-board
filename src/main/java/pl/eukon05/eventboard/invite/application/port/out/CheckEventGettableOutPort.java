package pl.eukon05.eventboard.invite.application.port.out;

import pl.eukon05.eventboard.common.Result;

public interface CheckEventGettableOutPort {
    Result checkEventGettable(String userId, long eventId);
}
