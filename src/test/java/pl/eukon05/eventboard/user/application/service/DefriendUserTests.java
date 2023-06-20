package pl.eukon05.eventboard.user.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.user.application.port.in.CheckIfFriendsPort;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.user.application.service.UnitTestUtils.*;

class DefriendUserTests {
    private final SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
    private final GetUserPort getUserPort = Mockito.mock(GetUserPort.class);
    private final CheckIfFriendsPort checkIfFriendsPort = Mockito.mock(CheckIfFriendsPort.class);
    private final DefriendUserUseCase defriendUserUseCase = new DefriendUserUseCase(getUserPort, saveUserPort, checkIfFriendsPort);

    @Test
    void should_defriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        Mockito.when(checkIfFriendsPort.checkIfFriends(one, two)).thenReturn(true);

        gettingUserByIdWillReturn(getUserPort, one.getId(), one);
        gettingUserByIdWillReturn(getUserPort, two.getId(), two);

        assertTrue(defriendUserUseCase.execute(one.getId(), two.getId()));
        verify(saveUserPort).saveUser(one);
        verify(saveUserPort).saveUser(two);

        assertThat(one.getFriends(), empty());
        assertThat(two.getFriends(), empty());
    }

    @Test
    void should_not_defriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        Mockito.when(checkIfFriendsPort.checkIfFriends(one, two)).thenReturn(false);

        gettingUserByIdWillReturn(getUserPort, one.getId(), one);
        gettingUserByIdWillReturn(getUserPort, two.getId(), two);

        assertFalse(defriendUserUseCase.execute(one.getId(), two.getId()));
    }
}
