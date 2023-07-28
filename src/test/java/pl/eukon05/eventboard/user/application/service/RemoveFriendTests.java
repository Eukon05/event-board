package pl.eukon05.eventboard.user.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.eukon05.eventboard.user.application.service.UnitTestUtils.*;

class RemoveFriendTests {
    private final SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
    private final GetUserPort getUserPort = Mockito.mock(GetUserPort.class);
    private final RemoveFriendUseCase removeFriendUseCase = new RemoveFriendUseCase(getUserPort, saveUserPort);

    @Test
    void should_remove_friend() {
        User one = createUserOne();
        User two = createUserTwo();

        one.getFriendIDs().add(two.getId());
        two.getFriendIDs().add(one.getId());

        gettingUserByIdWillReturn(getUserPort, one.getId(), one);
        gettingUserByIdWillReturn(getUserPort, two.getId(), two);

        assertEquals(Result.SUCCESS, removeFriendUseCase.removeFriend(one.getId(), two.getId()));
        verify(saveUserPort).saveUser(one);
        verify(saveUserPort).saveUser(two);

        assertThat(one.getFriendIDs(), empty());
        assertThat(two.getFriendIDs(), empty());
    }

    @Test
    void should_not_remove_friend() {
        User one = createUserOne();
        User two = createUserTwo();

        gettingUserByIdWillReturn(getUserPort, one.getId(), one);
        gettingUserByIdWillReturn(getUserPort, two.getId(), two);

        assertEquals(Result.USER_NOT_FRIEND, removeFriendUseCase.removeFriend(one.getId(), two.getId()));
    }

}
