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
class RemoveFriendUseCase {

    private final GetUserPort getUserPort;
    private final SaveUserPort saveUserPort;

    Result removeFriend(String selfID, String friendID) {
        Optional<User> selfOptional = getUserPort.getUserById(selfID);
        Optional<User> friendOptional = getUserPort.getUserById(friendID);

        if (selfOptional.isEmpty() || friendOptional.isEmpty())
            return Result.USER_NOT_FOUND;

        User self = selfOptional.get();
        User friend = friendOptional.get();

        Result result = self.removeFriend(friend);

        if (result.equals(Result.SUCCESS)) {
            saveUserPort.saveUser(self);
            saveUserPort.saveUser(friend);
        }

        return result;
    }
}
