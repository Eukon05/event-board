package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class DefriendUserUseCase {

    private final GetUserPort getUserPort;
    private final SaveUserPort saveUserPort;

    boolean execute(String selfID, String friendID) {
        Optional<User> selfOptional = getUserPort.getUserById(selfID);
        Optional<User> friendOptional = getUserPort.getUserById(friendID);

        if (selfOptional.isEmpty() || friendOptional.isEmpty())
            return false;

        User self = selfOptional.get();
        User friend = friendOptional.get();

        if (!self.getFriends().contains(friend) || !friend.getFriends().contains(self))
            return false;

        self.getFriends().remove(friend);
        friend.getFriends().remove(self);

        saveUserPort.saveUser(self);
        saveUserPort.saveUser(friend);

        return true;
    }
}
