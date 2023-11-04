package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.event.application.port.in.command.CreateEventCommand;
import pl.eukon05.eventboard.event.application.port.in.command.ModifyEventCommand;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventFacade {
    private final ManageEventAttendanceUseCase manageEventAttendanceUseCase;
    private final CreateEventUseCase createEventUseCase;
    private final DeleteEventUseCase deleteEventUseCase;
    private final InviteToEventUseCase inviteToEventUseCase;
    private final ModifyEventUseCase modifyEventUseCase;
    private final PublishEventUseCase publishEventUseCase;
    private final GetEventUseCase getEventUseCase;

    public ResultWrapper createEvent(String userID, CreateEventCommand command) {
        long id = createEventUseCase.create(userID, command);
        return ResultWrapper.builder().result(Result.SUCCESS).createdResourceID(id).build();
    }

    public ResultWrapper publishEvent(String userID, long eventID) {
        return ResultWrapper.wrap(publishEventUseCase.publish(userID, eventID));
    }

    public ResultWrapper attendEvent(String userID, long eventID) {
        return ResultWrapper.wrap(manageEventAttendanceUseCase.attend(userID, eventID));
    }

    public ResultWrapper unattendEvent(String userID, long eventID) {
        return ResultWrapper.wrap(manageEventAttendanceUseCase.unattend(userID, eventID));
    }

    public ResultWrapper inviteToEvent(String userID, String friendID, long eventID) {
        return ResultWrapper.wrap(inviteToEventUseCase.invite(userID, friendID, eventID));
    }

    public ResultWrapper deleteEvent(String userID, long eventID) {
        return ResultWrapper.wrap(deleteEventUseCase.delete(userID, eventID));
    }

    public ResultWrapper searchForEvent(Map<String, String> parameters, Pageable pageable) {
        return ResultWrapper.builder().result(Result.SUCCESS).data(getEventUseCase.search(parameters, pageable)).build();
    }

    public ResultWrapper searchForAttended(String userID, Pageable pageable) {
        return ResultWrapper
                .builder()
                .result(Result.SUCCESS)
                .data(getEventUseCase.getAttendedByUser(userID, pageable))
                .build();
    }

    public ResultWrapper searchForInvited(String userID, Pageable pageable) {
        return ResultWrapper
                .builder()
                .result(Result.SUCCESS)
                .data(getEventUseCase.getInvitedForUser(userID, pageable))
                .build();
    }

    public ResultWrapper searchForOrganized(String userID, Pageable pageable) {
        return ResultWrapper
                .builder()
                .result(Result.SUCCESS)
                .data(getEventUseCase.getOrganizedByUser(userID, pageable))
                .build();
    }

    public ResultWrapper modifyEvent(String userID, long eventID, ModifyEventCommand command) {
        return ResultWrapper.wrap(modifyEventUseCase.modify(userID, eventID, command));
    }

    public ResultWrapper getEvent(String userID, long eventID) {
        return getEventUseCase.getById(userID, eventID);
    }

}
