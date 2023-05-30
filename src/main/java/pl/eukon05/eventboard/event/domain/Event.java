package pl.eukon05.eventboard.event.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class Event {
    private final Long id;
    private final String organizerID;
    private String name;
    private String description;
    private Location location;
    private EventType type;
    private final Set<Integer> guestIDs;
    private final Set<Integer> inviteeIDs;
}
