package pl.eukon05.eventboard.event.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.eukon05.eventboard.event.domain.Event;

import java.util.Map;
import java.util.Optional;

public interface GetEventPort {
    Optional<Event> getById(long eventID);

    Page<Event> search(Map<String, String> parameters, Pageable pageable);

    Page<Event> getAttendedByUser(String userID, Pageable pageable);

    Page<Event> getInvitedForUser(String userID, Pageable pageable);

    Page<Event> getOrganizedByUser(String userID, Pageable pageable);
}
