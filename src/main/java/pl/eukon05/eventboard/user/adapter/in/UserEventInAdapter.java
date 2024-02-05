package pl.eukon05.eventboard.user.adapter.in;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.user.application.port.in.CheckIfFriendsInPort;
import pl.eukon05.eventboard.user.application.service.UserFacade;

@Adapter
@RequiredArgsConstructor
class UserEventInAdapter implements CheckIfFriendsInPort {
    private final UserFacade facade;

    @Override
    public boolean checkIfFriends(String userOne, String userTwo) {
        return facade.checkIfFriends(userOne, userTwo);
    }
}
