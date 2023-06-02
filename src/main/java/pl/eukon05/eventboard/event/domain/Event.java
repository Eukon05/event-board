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

    private String organizerID;
    private String name;
    private String description;
    private Location location;
    private EventType type;

    private final Set<String> guestIDs;
    private final Set<String> inviteeIDs;
    private final Set<String> likedIDs;

    public int getGuestsCount() {
        return guestIDs.size();
    }

    public int getInviteesCount() {
        return inviteeIDs.size();
    }

    public int getLikesCount() {
        return likedIDs.size();
    }
}
