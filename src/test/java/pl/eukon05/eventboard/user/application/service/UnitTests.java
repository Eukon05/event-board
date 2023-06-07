package pl.eukon05.eventboard.user.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

class UnitTests {

    private final SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
    private final GetUserPort getUserPort = Mockito.mock(GetUserPort.class);

    private final BefriendUserUseCase befriendUserUseCase = new BefriendUserUseCase(getUserPort, saveUserPort);
    private final DefriendUserUseCase defriendUserUseCase = new DefriendUserUseCase(getUserPort, saveUserPort);

    private User createUserOne() {
        return new User("userone", new HashSet<>());
    }

    private User createUserTwo() {
        return new User("usertwo", new HashSet<>());
    }

    @Test
    void should_befriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        Mockito.when(getUserPort.getUserById(one.getId())).thenReturn(Optional.of(one));
        Mockito.when(getUserPort.getUserById(two.getId())).thenReturn(Optional.of(two));

        assertTrue(befriendUserUseCase.execute(one.getId(), two.getId()));
        verify(saveUserPort).saveUser(one);
        verify(saveUserPort).saveUser(two);
        assertTrue(one.getFriends().contains(two));
        assertTrue(two.getFriends().contains(one));
    }

    @Test
    void should_not_befriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        one.getFriends().add(two);
        two.getFriends().add(one);

        Mockito.when(getUserPort.getUserById(one.getId())).thenReturn(Optional.of(one));
        Mockito.when(getUserPort.getUserById(two.getId())).thenReturn(Optional.of(two));

        assertFalse(befriendUserUseCase.execute(one.getId(), two.getId()));
    }

    @Test
    void should_not_befriend_self() {
        String id = "someid";
        assertFalse(befriendUserUseCase.execute(id, id));
    }

    @Test
    void should_defriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        one.getFriends().add(two);
        two.getFriends().add(one);

        Mockito.when(getUserPort.getUserById(one.getId())).thenReturn(Optional.of(one));
        Mockito.when(getUserPort.getUserById(two.getId())).thenReturn(Optional.of(two));

        assertTrue(defriendUserUseCase.execute(one.getId(), two.getId()));
        verify(saveUserPort).saveUser(one);
        verify(saveUserPort).saveUser(two);
        assertFalse(one.getFriends().contains(two));
        assertFalse(two.getFriends().contains(one));
    }

    @Test
    void should_not_defriend_user() {
        User one = createUserOne();
        User two = createUserTwo();

        Mockito.when(getUserPort.getUserById(one.getId())).thenReturn(Optional.of(one));
        Mockito.when(getUserPort.getUserById(two.getId())).thenReturn(Optional.of(two));

        assertFalse(defriendUserUseCase.execute(one.getId(), two.getId()));
    }

}
