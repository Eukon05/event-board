package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.in.GetUserFriendlistPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;

import java.util.List;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class InviteToEventUseCase {
    private final GetUserFriendlistPort getUserFriendlistPort;
    private final GetEventPort getEventPort;
    private final SaveEventPort saveEventPort;

    boolean execute(String selfID, String friendID, long eventID) {
        Optional<Event> eventOptional = getEventPort.getEventById(eventID);

        if (eventOptional.isEmpty()) return false;

        Event event = eventOptional.get();

        List<String> friendlist = getUserFriendlistPort.getUserFriendlist(selfID);

        if (!friendlist.contains(friendID)) return false;

        if (event.getGuestIDs().contains(friendID) || event.getInviteeIDs().contains(friendID)) return false;

        if (event.getType().equals(EventType.PRIVATE) && !event.getGuestIDs().contains(selfID) && !event.getOrganizerID().equals(selfID))
            return false;

        event.getInviteeIDs().add(friendID);
        saveEventPort.saveEvent(event);

        return true;
    }

}
