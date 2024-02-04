package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.event.application.port.in.command.CreateEventCommand;
import pl.eukon05.eventboard.event.application.port.in.command.ModifyEventCommand;
import pl.eukon05.eventboard.event.application.port.out.dto.EventDTO;

import java.util.Map;

import static pl.eukon05.eventboard.common.ResultWrapper.NO_CONTENT;

@Service
@RequiredArgsConstructor
public class EventFacade {
    private final ManageEventAttendanceUseCase manageEventAttendanceUseCase;
    private final CreateEventUseCase createEventUseCase;
    private final DeleteEventUseCase deleteEventUseCase;
    private final ModifyEventUseCase modifyEventUseCase;
    private final ManageEventVisibilityUseCase manageEventVisibilityUseCase;
    private final GetEventUseCase getEventUseCase;

    public ResultWrapper<String> createEvent(String userID, CreateEventCommand command) {
        long id = createEventUseCase.create(userID, command);
        return ResultWrapper.<String>builder().result(Result.SUCCESS).data(NO_CONTENT).createdResourceID(id).build();
    }

    public ResultWrapper<String> publishEvent(String userID, long eventID) {
        return ResultWrapper.wrap(manageEventVisibilityUseCase.publish(userID, eventID));
    }

    public ResultWrapper<String> unpublishEvent(String userID, long eventID) {
        return ResultWrapper.wrap(manageEventVisibilityUseCase.unpublish(userID, eventID));
    }

    public ResultWrapper<String> attendEvent(String userID, long eventID) {
        return ResultWrapper.wrap(manageEventAttendanceUseCase.attend(userID, eventID));
    }

    public ResultWrapper<String> unattendEvent(String userID, long eventID) {
        return ResultWrapper.wrap(manageEventAttendanceUseCase.unattend(userID, eventID));
    }

    public ResultWrapper deleteEvent(String userID, long eventID) {
        return ResultWrapper.wrap(deleteEventUseCase.delete(userID, eventID));
    }

    public ResultWrapper<Page<EventDTO>> searchForEvent(Map<String, String> parameters, Pageable pageable) {
        return ResultWrapper.<Page<EventDTO>>builder().result(Result.SUCCESS).data(getEventUseCase.search(parameters, pageable)).build();
    }

    public ResultWrapper<Page<EventDTO>> searchForAttended(String userID, Pageable pageable) {
        return ResultWrapper
                .<Page<EventDTO>>builder()
                .result(Result.SUCCESS)
                .data(getEventUseCase.getAttendedByUser(userID, pageable))
                .build();
    }

    public ResultWrapper<Page<EventDTO>> searchForOrganized(String userID, Pageable pageable) {
        return ResultWrapper
                .<Page<EventDTO>>builder()
                .result(Result.SUCCESS)
                .data(getEventUseCase.getOrganizedByUser(userID, pageable))
                .build();
    }

    public ResultWrapper<String> modifyEvent(String userID, long eventID, ModifyEventCommand command) {
        return ResultWrapper.wrap(modifyEventUseCase.modify(userID, eventID, command));
    }

    public ResultWrapper<?> getEvent(String userID, long eventID) {
        return getEventUseCase.getById(userID, eventID);
    }
}
