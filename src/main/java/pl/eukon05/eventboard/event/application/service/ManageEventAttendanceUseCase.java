package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class ManageEventAttendanceUseCase {
    private final GetEventPort getEventPort;
    private final SaveEventPort saveEventPort;

    Result attend(String userID, long eventID) {
        Optional<Event> eventOptional = getEventPort.getById(eventID);

        if (eventOptional.isEmpty()) return Result.EVENT_NOT_FOUND;

        Event event = eventOptional.get();

        Result result = event.attend(userID);

        if (result.equals(Result.SUCCESS)) saveEventPort.saveEvent(event);

        return result;
    }

    Result unattend(String userID, long eventID) {
        Optional<Event> eventOptional = getEventPort.getById(eventID);

        if (eventOptional.isEmpty()) return Result.EVENT_NOT_FOUND;

        Event event = eventOptional.get();

        Result result = event.unattend(userID);

        if (result.equals(Result.SUCCESS)) saveEventPort.saveEvent(event);

        return result;
    }
}
