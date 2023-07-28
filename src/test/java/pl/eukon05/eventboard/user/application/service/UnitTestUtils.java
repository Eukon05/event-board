package pl.eukon05.eventboard.user.application.service;

import org.mockito.Mockito;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.domain.User;

import java.util.Optional;

final class UnitTestUtils {
    static User createUserOne() {
        return new User("userone");
    }

    static User createUserTwo() {
        return new User("usertwo");
    }

    static void gettingUserByIdWillReturn(GetUserPort port, String id, User user) {
        Mockito.when(port.getUserById(id)).thenReturn(Optional.of(user));
    }
}
