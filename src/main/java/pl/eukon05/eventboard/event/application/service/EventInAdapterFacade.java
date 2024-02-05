package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.eukon05.eventboard.common.Result;

@Service
@RequiredArgsConstructor
public class EventInAdapterFacade {
    private final GetEventUseCase getEventUseCase;
    private final CheckUserHostUseCase checkUserHostUseCase;
    private final CheckUserAttendeeUseCase checkUserAttendeeUseCase;

    public Result checkEventGettable(String userId, long eventId) {
        return getEventUseCase.getById(userId, eventId).getResult();
    }

    public boolean checkUserHost(String userId, long eventId) {
        return checkUserHostUseCase.checkUserHost(userId, eventId);
    }

    public boolean checkUserAttendee(String userId, long eventId) {
        return checkUserAttendeeUseCase.checkUserAttendee(userId, eventId);
    }

}
