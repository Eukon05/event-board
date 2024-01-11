package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.out.CheckUserInvitedOutPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.dto.EventDTO;
import pl.eukon05.eventboard.event.application.port.out.dto.EventDTOMapper;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

import java.util.Map;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class GetEventUseCase {
    private final CheckUserInvitedOutPort checkUserInvitedOutPort;
    private final GetEventPort getEventPort;
    private final EventDTOMapper mapper;

    ResultWrapper getById(String userID, long eventID) {
        Optional<Event> eventOptional = getEventPort.getById(eventID);

        if (eventOptional.isEmpty()) return ResultWrapper.wrap(Result.EVENT_NOT_FOUND);

        Event event = eventOptional.get();

        boolean isUserInvited = checkUserInvitedOutPort.checkUserInvited(userID, eventID);

        if (event.getType().equals(EventType.PRIVATE) && !event.getOrganizerID().equals(userID) && !event.getGuestIDs().contains(userID) && !isUserInvited)
            return ResultWrapper.wrap(Result.EVENT_PRIVATE);

        return ResultWrapper.builder().result(Result.SUCCESS).data(mapper.mapDomainToDTO(event)).build();
    }

    Page<EventDTO> search(Map<String, String> parameters, Pageable pageable) {
        return getEventPort.search(parameters, pageable).map(mapper::mapDomainToDTO);
    }

    Page<EventDTO> getAttendedByUser(String userID, Pageable pageable) {
        return getEventPort.getAttendedByUser(userID, pageable).map(mapper::mapDomainToDTO);
    }

    Page<EventDTO> getOrganizedByUser(String userID, Pageable pageable) {
        return getEventPort.getOrganizedByUser(userID, pageable).map(mapper::mapDomainToDTO);
    }
}
