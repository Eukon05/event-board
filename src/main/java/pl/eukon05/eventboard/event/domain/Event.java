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
    private final Set<String> likedIDs;

    public Result attend(String userID, boolean isUserInvited) {
        if (type.equals(EventType.PRIVATE) && !isUserInvited) return Result.EVENT_PRIVATE;

        if (organizerID.equals(userID)) return Result.ATTEND_SELF_ORGANIZED_EVENT;
        if (guestIDs.contains(userID)) return Result.ALREADY_ATTENDEE;

        guestIDs.add(userID);

        return Result.SUCCESS;
    }

    public Result unattend(String userID) {
        if (!guestIDs.contains(userID)) return Result.NOT_ATTENDEE;

        guestIDs.remove(userID);

        return Result.SUCCESS;
    }
}
