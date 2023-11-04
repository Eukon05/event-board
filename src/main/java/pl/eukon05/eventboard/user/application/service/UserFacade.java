package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public ResultWrapper createFriendRequest(String selfID, String friendID) {
        return ResultWrapper.wrap(createFriendRequestUseCase.createFriendRequest(selfID, friendID));
    }

    public ResultWrapper acceptFriendRequest(String selfID, String friendID) {
        return ResultWrapper.wrap(manageFriendRequestUseCase.acceptFriendRequest(selfID, friendID));
    }

    public ResultWrapper rejectFriendRequest(String selfID, String friendID) {
        return ResultWrapper.wrap(manageFriendRequestUseCase.rejectFriendRequest(selfID, friendID));
    }

    public ResultWrapper removeFriend(String selfID, String friendID) {
        return ResultWrapper.wrap(removeFriendUseCase.removeFriend(selfID, friendID));
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
