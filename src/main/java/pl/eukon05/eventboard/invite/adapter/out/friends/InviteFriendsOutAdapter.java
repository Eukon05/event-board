package pl.eukon05.eventboard.invite.adapter.out.friends;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.invite.application.port.out.CheckIfFriendsOutPort;
import pl.eukon05.eventboard.user.application.port.in.CheckIfFriendsInPort;

@Adapter
@RequiredArgsConstructor
class InviteFriendsOutAdapter implements CheckIfFriendsOutPort {
    private final CheckIfFriendsInPort checkIfFriendsInPort;

    @Override
    public boolean checkIfFriends(String userOne, String userTwo) {
        return checkIfFriendsInPort.checkIfFriends(userOne, userTwo);
    }
}