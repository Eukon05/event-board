package pl.eukon05.eventboard.user.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.user.application.service.UnitTestUtils.*;

class BefriendUserTests {
    private final SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
    private final GetUserPort getUserPort = Mockito.mock(GetUserPort.class);
    private final BefriendUserUseCase befriendUserUseCase = new BefriendUserUseCase(getUserPort, saveUserPort);

    @Test
    void should_befriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        gettingUserByIdWillReturn(getUserPort, one.getId(), one);
        gettingUserByIdWillReturn(getUserPort, two.getId(), two);

        assertTrue(befriendUserUseCase.execute(one.getId(), two.getId()));
        verify(saveUserPort).saveUser(one);
        verify(saveUserPort).saveUser(two);

        assertThat(one.getFriends(), contains(two));
        assertThat(two.getFriends(), contains(one));
    }

    @Test
    void should_not_befriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        one.getFriends().add(two);
        two.getFriends().add(one);

        gettingUserByIdWillReturn(getUserPort, one.getId(), one);
        gettingUserByIdWillReturn(getUserPort, two.getId(), two);

        assertFalse(befriendUserUseCase.execute(one.getId(), two.getId()));
    }

    @Test
    void should_not_befriend_self() {
        String id = "someid";
        assertFalse(befriendUserUseCase.execute(id, id));
    }

}
