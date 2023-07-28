package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.user.application.port.in.CheckIfFriendsPort;

@Service
@RequiredArgsConstructor
public class UserFacade implements CheckIfFriendsPort {

    private final CreateFriendRequestUseCase createFriendRequestUseCase;
    private final RemoveFriendUseCase removeFriendUseCase;
    private final CheckIfFriendsUseCase checkIfFriendsUseCase;
    private final ManageFriendRequestUseCase manageFriendRequestUseCase;
    private final GetFriendsUseCase getFriendsUseCase;

    public Result createFriendRequest(String selfID, String friendID) {
        return createFriendRequestUseCase.createFriendRequest(selfID, friendID);
    }

    public Result acceptFriendRequest(String selfID, String friendID) {
        return manageFriendRequestUseCase.acceptFriendRequest(selfID, friendID);
    }

    public Result rejectFriendRequest(String selfID, String friendID) {
        return manageFriendRequestUseCase.rejectFriendRequest(selfID, friendID);
    }

    public Result removeFriend(String selfID, String friendID) {
        return removeFriendUseCase.removeFriend(selfID, friendID);
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
