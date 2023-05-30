package pl.eukon05.eventboard.user.domain;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class User {
    private String id;
    private UserType type;
    private final Set<Integer> hostedEventsIDs;
    private final Set<Integer> participatingEventsIDs;
    private final Set<Integer> likedEventsIDs;
}
