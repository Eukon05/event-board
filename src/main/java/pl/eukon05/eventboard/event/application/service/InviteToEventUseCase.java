package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.out.CheckIfFriendsPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class InviteToEventUseCase {
    private final CheckIfFriendsPort checkIfFriendsPort;
    private final GetEventPort getEventPort;
    private final SaveEventPort saveEventPort;

    boolean execute(String selfID, String friendID, long eventID) {
        Optional<Event> eventOptional = getEventPort.getEventById(eventID);

        if (eventOptional.isEmpty()) return false;

        Event event = eventOptional.get();

        if (!checkIfFriendsPort.checkIfFriends(selfID, friendID)) return false;

        boolean result = event.invite(selfID, friendID);

        if (result)
            saveEventPort.saveEvent(event);

        return result;
    }

}
