package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class BefriendUserUseCase {

    private final GetUserPort getUserPort;
    private final SaveUserPort saveUserPort;

    Result execute(String selfID, String friendID) {
        if (selfID.equals(friendID))
            return Result.BEFRIEND_SELF;

        Optional<User> selfOptional = getUserPort.getUserById(selfID);
        Optional<User> friendOptional = getUserPort.getUserById(friendID);

        if (selfOptional.isEmpty() || friendOptional.isEmpty())
            return Result.USER_NOT_FOUND;

        User self = selfOptional.get();
        User friend = friendOptional.get();

        if (self.friendIDs().contains(friendID))
            return Result.USER_ALREADY_FRIEND;

        self.friendIDs().add(friendID);
        friend.friendIDs().add(selfID);

        saveUserPort.saveUser(self);
        saveUserPort.saveUser(friend);

        return Result.SUCCESS;
    }

}
