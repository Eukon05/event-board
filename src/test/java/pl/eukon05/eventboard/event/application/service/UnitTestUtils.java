package pl.eukon05.eventboard.event.application.service;

import org.mockito.Mockito;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.domain.Event;
import pl.eukon05.eventboard.event.domain.EventType;
import pl.eukon05.eventboard.event.domain.Location;

import java.util.HashSet;
import java.util.Optional;

final class UnitTestUtils {
    static final String userID = "someid";
    static final String friendID = "somefriendid";

    static Event createTestPublicEvent() {
        return new Event(1L, "", "name", "desc", new Location("sas", "sas", "sas"), EventType.PUBLIC, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    static void gettingEventWillReturn(GetEventPort port, Event event) {
        Mockito.when(port.getEventById(Mockito.anyLong())).thenReturn(Optional.of(event));
    }
}
