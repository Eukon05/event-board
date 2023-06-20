package pl.eukon05.eventboard.event.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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

    private LocalDate startDate;
    private LocalDate endDate;

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

    public boolean attend(String userID) {
        if (guestIDs.contains(userID)) return false;

        if (type.equals(EventType.PRIVATE) && !inviteeIDs.contains(userID)) return false;

        guestIDs.add(userID);
        inviteeIDs.remove(userID);

        return true;
    }

    public boolean invite(String inviterID, String inviteeID) {
        if (guestIDs.contains(inviteeID) || inviteeIDs.contains(inviteeID)) return false;

        if (type.equals(EventType.PRIVATE) && !guestIDs.contains(inviterID) && !organizerID.equals(inviterID))
            return false;

        inviteeIDs.add(inviteeID);

        return true;
    }
}
