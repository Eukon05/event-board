package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.user.application.port.in.CheckIfFriendsPort;

@Service
@RequiredArgsConstructor
public class UserFacade implements CheckIfFriendsPort {

    private final BefriendUserUseCase befriendUserUseCase;
    private final DefriendUserUseCase defriendUserUseCase;
    private final CheckIfFriendsUseCase checkIfFriendsUseCase;
    private final ManageFriendRequestUseCase manageFriendRequestUseCase;
    private final GetFriendsUseCase getFriendsUseCase;

    public Result befriend(String selfID, String friendID) {
        return befriendUserUseCase.execute(selfID, friendID);
    }

    public Result acceptFriendRequest(String selfID, String friendID) {
        return manageFriendRequestUseCase.acceptFriendRequest(selfID, friendID);
    }

    public Result rejectFriendRequest(String selfID, String friendID) {
        return manageFriendRequestUseCase.rejectFriendRequest(selfID, friendID);
    }

    public Result defriend(String selfID, String friendID) {
        return defriendUserUseCase.execute(selfID, friendID);
    }

    @Override
    public boolean checkIfFriends(String userOne, String userTwo) {
        return checkIfFriendsUseCase.execute(userOne, userTwo);
    }

    public ResultWrapper getFriends(String userID) {
        return getFriendsUseCase.getFriends(userID);
    }

    public ResultWrapper getFriendRequests(String userID) {
        return getFriendsUseCase.getFriendRequests(userID);
    }

}
