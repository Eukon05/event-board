package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.user.application.port.in.CheckIfFriendsPort;

@Service
@RequiredArgsConstructor
public class UserFacade implements CheckIfFriendsPort {

    private final BefriendUserUseCase befriendUserUseCase;
    private final DefriendUserUseCase defriendUserUseCase;
    private final CheckIfFriendsUseCase checkIfFriendsUseCase;

    public Result befriend(String selfID, String friendID) {
        return befriendUserUseCase.execute(selfID, friendID);
    }

    public Result defriend(String selfID, String friendID) {
        return defriendUserUseCase.execute(selfID, friendID);
    }

    @Override
    public boolean checkIfFriends(String userOne, String userTwo) {
        return checkIfFriendsUseCase.execute(userOne, userTwo);
    }
}
