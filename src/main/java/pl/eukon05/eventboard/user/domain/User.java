package pl.eukon05.eventboard.user.domain;


import java.util.Set;

public record User(String id, Set<String> friendIDs) {
}
