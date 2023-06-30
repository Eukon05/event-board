package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.user.application.port.in.CheckIfFriendsPort;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.domain.User;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class CheckIfFriendsUseCase implements CheckIfFriendsPort {
    private final GetUserPort getUserPort;

    public boolean checkIfFriends(String userOne, String userTwo) {
        Optional<User> userOptional = getUserPort.getUserById(userOne);

        if (userOptional.isEmpty()) return false;

        User user = userOptional.get();

        return user.friendIDs().contains(userTwo);
    }

    public boolean checkIfFriends(User userOne, User userTwo) {
        return userOne.friendIDs().contains(userTwo.id());
    }

}
