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
class ManageFriendRequestUseCase {

    private final GetUserPort getUserPort;
    private final SaveUserPort saveUserPort;

    Result acceptFriendRequest(String selfID, String friendID) {
        if (selfID.equals(friendID)) return Result.BEFRIEND_SELF;

        Optional<User> selfOptional = getUserPort.getUserById(selfID);
        Optional<User> friendOptional = getUserPort.getUserById(friendID);

        if (selfOptional.isEmpty() || friendOptional.isEmpty()) return Result.USER_NOT_FOUND;

        User self = selfOptional.get();
        User friend = friendOptional.get();

        Result result = self.acceptFriendRequest(friend);

        if (result.equals(Result.SUCCESS)) {
            saveUserPort.saveUser(self);
            saveUserPort.saveUser(friend);
        }

        return result;
    }

    Result rejectFriendRequest(String selfID, String friendID) {
        if (selfID.equals(friendID)) return Result.BEFRIEND_SELF;

        Optional<User> selfOptional = getUserPort.getUserById(selfID);

        if (selfOptional.isEmpty()) return Result.USER_NOT_FOUND;

        User self = selfOptional.get();

        Result result = self.rejectFriendRequest(friendID);

        if (result.equals(Result.SUCCESS)) saveUserPort.saveUser(self);

        return result;
    }

}
