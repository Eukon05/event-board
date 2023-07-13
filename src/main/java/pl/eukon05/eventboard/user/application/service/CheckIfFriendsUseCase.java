package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.domain.User;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class CheckIfFriendsUseCase {
    private final GetUserPort getUserPort;

    public boolean execute(String userOne, String userTwo) {
        Optional<User> userOptional = getUserPort.getUserById(userOne);

        if (userOptional.isEmpty()) return false;

        User user = userOptional.get();

        return user.friendIDs().contains(userTwo);
    }

}
