package pl.eukon05.eventboard.user.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.user.application.service.UserUnitTestUtils.*;

class CreateFriendRequestUnitTests {
    private final SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
    private final GetUserPort getUserPort = Mockito.mock(GetUserPort.class);
    private final CreateFriendRequestUseCase createFriendRequestUseCase = new CreateFriendRequestUseCase(getUserPort, saveUserPort);

    @Test
    void should_befriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        gettingUserByIdWillReturn(getUserPort, one.getId(), one);
        gettingUserByIdWillReturn(getUserPort, two.getId(), two);

        assertEquals(Result.SUCCESS, createFriendRequestUseCase.createFriendRequest(one.getId(), two.getId()));

        verify(saveUserPort).saveUser(two);

        assertThat(two.getFriendRequestIDs(), contains(one.getId()));
    }

    @Test
    void should_not_befriend_friend() {
        User one = createUserOne();
        User two = createUserTwo();

        one.getFriendIDs().add(two.getId());
        two.getFriendIDs().add(one.getId());

        gettingUserByIdWillReturn(getUserPort, one.getId(), one);
        gettingUserByIdWillReturn(getUserPort, two.getId(), two);

        assertEquals(Result.USER_ALREADY_FRIEND, createFriendRequestUseCase.createFriendRequest(one.getId(), two.getId()));
    }

    @Test
    void should_not_befriend_user_twice() {
        User one = createUserOne();
        User two = createUserTwo();

        gettingUserByIdWillReturn(getUserPort, one.getId(), one);
        gettingUserByIdWillReturn(getUserPort, two.getId(), two);

        two.getFriendRequestIDs().add(one.getId());
        assertEquals(Result.FRIEND_REQUEST_ALREADY_SENT, createFriendRequestUseCase.createFriendRequest(one.getId(), two.getId()));
    }

    @Test
    void should_not_befriend_self() {
        String id = "someid";
        assertEquals(Result.BEFRIEND_SELF, createFriendRequestUseCase.createFriendRequest(id, id));
    }

}
