package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.dto.EventDTOMapper;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class GetEventUseCase {
    private final GetEventPort getEventPort;
    private final EventDTOMapper mapper;

    ResultWrapper execute(String userID, long eventID) {
        Optional<Event> eventOptional = getEventPort.getEventById(eventID);

        if (eventOptional.isEmpty()) return ResultWrapper.of(Result.EVENT_NOT_FOUND);

        Event event = eventOptional.get();

        if (event.getType().equals(EventType.PRIVATE) && !event.getOrganizerID().equals(userID) && !event.getGuestIDs().contains(userID) && !event.getInviteeIDs().contains(userID))
            return ResultWrapper.of(Result.EVENT_PRIVATE);

        return ResultWrapper.of(Result.SUCCESS, mapper.mapDomainToDTO(event));
    }
}
