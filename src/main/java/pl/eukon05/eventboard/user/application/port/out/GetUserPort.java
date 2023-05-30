package pl.eukon05.eventboard.user.application.port.out;

import pl.eukon05.eventboard.user.domain.User;

import java.util.Optional;

public interface GetUserPort {
    Optional<User> getUserById(String id);
}
