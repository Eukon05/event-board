package pl.eukon05.eventboard.event.adapter.out.friends;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.event.application.port.out.CheckIfFriendsPort;

@Adapter
@RequiredArgsConstructor
class FriendsAdapter implements CheckIfFriendsPort {
    private final pl.eukon05.eventboard.user.application.port.in.CheckIfFriendsPort checkIfFriendsPort;

    @Override
    public boolean checkIfFriends(String userOne, String userTwo) {
        return checkIfFriendsPort.checkIfFriends(userOne, userTwo);
    }
}
