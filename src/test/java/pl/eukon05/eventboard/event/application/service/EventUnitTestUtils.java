package pl.eukon05.eventboard.event.application.service;

import org.mockito.Mockito;
import pl.eukon05.eventboard.event.application.port.out.CheckUserInvitedOutPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;
import pl.eukon05.eventboard.event.domain.Location;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

final class EventUnitTestUtils {
    static final String userID = "someid";

    static Event createTestPublicEvent() {
        return new Event(1L, "", "name", "desc", new Location("de", "getCity", "sas", "sas", "sas"), EventType.PUBLIC, LocalDate.now(), LocalDate.now().plusDays(1), new HashSet<>(), new HashSet<>());
    }

    static void gettingEventWillReturn(GetEventPort port, Event event) {
        Mockito.when(port.getById(Mockito.anyLong())).thenReturn(Optional.of(event));
    }

    static void checkingInvitedWillReturn(CheckUserInvitedOutPort checkUserInvitedOutPort, Event event, boolean val) {
        Mockito.when(checkUserInvitedOutPort.checkUserInvited(userID, event.getId())).thenReturn(val);
    }
}
