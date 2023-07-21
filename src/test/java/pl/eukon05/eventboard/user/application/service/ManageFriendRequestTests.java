package pl.eukon05.eventboard.user.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.user.application.service.UnitTestUtils.*;

class ManageFriendRequestTests {

    private final SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
    private final GetUserPort getUserPort = Mockito.mock(GetUserPort.class);
    private final ManageFriendRequestUseCase manageFriendRequestUseCase = new ManageFriendRequestUseCase(getUserPort, saveUserPort);

    @Test
    void should_accept_friend_request() {
        User one = createUserOne();
        User two = createUserTwo();

        one.friendRequestIDs().add(two.id());

        gettingUserByIdWillReturn(getUserPort, one.id(), one);
        gettingUserByIdWillReturn(getUserPort, two.id(), two);

        assertEquals(Result.SUCCESS, manageFriendRequestUseCase.acceptFriendRequest(one.id(), two.id()));

        verify(saveUserPort).saveUser(one);
        verify(saveUserPort).saveUser(two);

        assertThat(one.friendIDs(), contains(two.id()));
        assertThat(two.friendIDs(), contains(one.id()));
    }

    @Test
    void should_not_accept_nonexistent_friend_request() {
        User one = createUserOne();
        User two = createUserTwo();

        gettingUserByIdWillReturn(getUserPort, one.id(), one);
        gettingUserByIdWillReturn(getUserPort, two.id(), two);

        assertEquals(Result.FRIEND_NOT_REQUESTED, manageFriendRequestUseCase.acceptFriendRequest(one.id(), two.id()));
    }

    @Test
    void should_reject_friend_request() {
        User one = createUserOne();
        User two = createUserTwo();

        one.friendRequestIDs().add(two.id());

        gettingUserByIdWillReturn(getUserPort, one.id(), one);
        gettingUserByIdWillReturn(getUserPort, two.id(), two);

        assertEquals(Result.SUCCESS, manageFriendRequestUseCase.rejectFriendRequest(one.id(), two.id()));

        verify(saveUserPort).saveUser(one);

        assertThat(one.friendIDs(), not(contains(two.id())));
        assertThat(one.friendRequestIDs(), not(contains(two.id())));
    }

    @Test
    void should_not_reject_nonexistent_friend_request() {
        User one = createUserOne();
        User two = createUserTwo();

        gettingUserByIdWillReturn(getUserPort, one.id(), one);
        gettingUserByIdWillReturn(getUserPort, two.id(), two);

        assertEquals(Result.FRIEND_NOT_REQUESTED, manageFriendRequestUseCase.rejectFriendRequest(one.id(), two.id()));
    }

}
