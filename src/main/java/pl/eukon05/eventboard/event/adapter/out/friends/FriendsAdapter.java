package pl.eukon05.eventboard.event.adapter.out.friends;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.event.application.port.out.CheckIfFriendsPort;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.domain.User;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
class FriendsAdapter implements CheckIfFriendsPort {
    private final GetUserPort getUserPort;

    @Override
    public boolean checkIfFriends(String userOne, String userTwo) {
        Optional<User> userOptional = getUserPort.getUserById(userOne);

        if (userOptional.isEmpty())
            return false;

        User user = userOptional.get();

        return user.getFriends().stream().map(User::getId).toList().contains(userTwo);
    }
}
