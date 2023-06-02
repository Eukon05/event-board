package pl.eukon05.eventboard.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class User {
    private String id;

    Set<User> friends;
}
