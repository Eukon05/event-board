package pl.eukon05.eventboard.user.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.user.application.port.in.CheckIfFriendsPort;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

        gettingUserByIdWillReturn(getUserPort, one.id(), one);
        gettingUserByIdWillReturn(getUserPort, two.id(), two);

        assertEquals(Result.SUCCESS, defriendUserUseCase.execute(one.id(), two.id()));
        verify(saveUserPort).saveUser(one);
        verify(saveUserPort).saveUser(two);

        assertThat(one.friendIDs(), empty());
        assertThat(two.friendIDs(), empty());
    }

    @Test
    void should_not_defriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        Mockito.when(checkIfFriendsPort.checkIfFriends(one, two)).thenReturn(false);

        gettingUserByIdWillReturn(getUserPort, one.id(), one);
        gettingUserByIdWillReturn(getUserPort, two.id(), two);

        assertEquals(Result.USER_NOT_FRIEND, defriendUserUseCase.execute(one.id(), two.id()));
    }
}
