package pl.eukon05.eventboard.user.application.port.out;

import pl.eukon05.eventboard.user.domain.User;

public interface SaveUserPort {
    void saveUser(User user);
}
