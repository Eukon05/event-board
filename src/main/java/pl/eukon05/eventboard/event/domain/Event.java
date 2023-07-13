package pl.eukon05.eventboard.event.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.eukon05.eventboard.common.Result;

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

    public Result attend(String userID) {
        if (guestIDs.contains(userID)) return Result.ALREADY_ATTENDEE;

        if (type.equals(EventType.PRIVATE) && !inviteeIDs.contains(userID)) return Result.EVENT_PRIVATE;

        guestIDs.add(userID);
        inviteeIDs.remove(userID);

        return Result.SUCCESS;
    }

    public Result invite(String inviterID, String inviteeID) {
        if (guestIDs.contains(inviteeID))
            return Result.ALREADY_ATTENDEE;

        else if (inviteeIDs.contains(inviteeID))
            return Result.ALREADY_INVITEE;

        if (type.equals(EventType.PRIVATE) && !guestIDs.contains(inviterID) && !organizerID.equals(inviterID))
            return Result.EVENT_PRIVATE;

        inviteeIDs.add(inviteeID);

        return Result.SUCCESS;
    }
}
