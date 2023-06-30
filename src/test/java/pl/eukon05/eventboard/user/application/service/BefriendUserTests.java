package pl.eukon05.eventboard.user.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.user.application.port.in.CheckIfFriendsPort;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.user.application.service.UnitTestUtils.*;

class BefriendUserTests {
    private final SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
    private final GetUserPort getUserPort = Mockito.mock(GetUserPort.class);
    private final CheckIfFriendsPort checkIfFriendsPort = Mockito.mock(CheckIfFriendsPort.class);
    private final BefriendUserUseCase befriendUserUseCase = new BefriendUserUseCase(getUserPort, saveUserPort, checkIfFriendsPort);

    @Test
    void should_befriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        gettingUserByIdWillReturn(getUserPort, one.id(), one);
        gettingUserByIdWillReturn(getUserPort, two.id(), two);

        checkingFriendsWillReturn(one, two, false);

        assertEquals(Result.SUCCESS, befriendUserUseCase.execute(one.id(), two.id()));
        verify(saveUserPort).saveUser(one);
        verify(saveUserPort).saveUser(two);

        assertThat(one.friendIDs(), contains(two.id()));
        assertThat(two.friendIDs(), contains(one.id()));
    }

    @Test
    void should_not_befriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        checkingFriendsWillReturn(one, two, true);

        gettingUserByIdWillReturn(getUserPort, one.id(), one);
        gettingUserByIdWillReturn(getUserPort, two.id(), two);

        assertEquals(Result.USER_ALREADY_FRIEND, befriendUserUseCase.execute(one.id(), two.id()));
    }

    @Test
    void should_not_befriend_self() {
        String id = "someid";
        assertEquals(Result.BEFRIEND_SELF, befriendUserUseCase.execute(id, id));
    }

    private void checkingFriendsWillReturn(User one, User two, boolean value) {
        Mockito.when(checkIfFriendsPort.checkIfFriends(one, two)).thenReturn(value);
    }

}
