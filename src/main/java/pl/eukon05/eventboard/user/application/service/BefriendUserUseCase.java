package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.user.application.port.in.CheckIfFriendsPort;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class BefriendUserUseCase {

    private final GetUserPort getUserPort;
    private final SaveUserPort saveUserPort;
    private final CheckIfFriendsPort checkIfFriendsPort;

    boolean execute(String selfID, String friendID) {
        if (selfID.equals(friendID))
            return false;

        Optional<User> selfOptional = getUserPort.getUserById(selfID);
        Optional<User> friendOptional = getUserPort.getUserById(friendID);

        if (selfOptional.isEmpty() || friendOptional.isEmpty())
            return false;

        User self = selfOptional.get();
        User friend = friendOptional.get();

        if (checkIfFriendsPort.checkIfFriends(self, friend))
            return false;

        self.getFriends().add(friend);
        friend.getFriends().add(self);

        saveUserPort.saveUser(self);
        saveUserPort.saveUser(friend);

        return true;
    }

}
