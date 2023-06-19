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

    @Override
    public boolean checkIfFriends(String userOne, String userTwo) {
        Optional<User> userOptional = getUserPort.getUserById(userOne);

        if (userOptional.isEmpty()) return false;

        User user = userOptional.get();

        return user.getFriends().stream().map(User::getId).toList().contains(userTwo);
    }

    @Override
    public boolean checkIfFriends(User userOne, User userTwo) {
        return userOne.getFriends().contains(userTwo) && userTwo.getFriends().contains(userOne);
    }

}
