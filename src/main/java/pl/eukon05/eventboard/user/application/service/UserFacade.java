package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.eukon05.eventboard.common.ResultWrapper;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final CreateUserUseCase createUserUseCase;
    private final CreateFriendRequestUseCase createFriendRequestUseCase;
    private final RemoveFriendUseCase removeFriendUseCase;
    private final CheckIfFriendsUseCase checkIfFriendsUseCase;
    private final ManageFriendRequestUseCase manageFriendRequestUseCase;
    private final GetFriendsUseCase getFriendsUseCase;

    public void createUser(String id) {
        createUserUseCase.createUser(id);
    }
    public ResultWrapper<String> createFriendRequest(String selfID, String friendID) {
        return ResultWrapper.wrap(createFriendRequestUseCase.createFriendRequest(selfID, friendID));
    }

    public ResultWrapper<String> acceptFriendRequest(String selfID, String friendID) {
        return ResultWrapper.wrap(manageFriendRequestUseCase.acceptFriendRequest(selfID, friendID));
    }

    public ResultWrapper<String> rejectFriendRequest(String selfID, String friendID) {
        return ResultWrapper.wrap(manageFriendRequestUseCase.rejectFriendRequest(selfID, friendID));
    }

    public ResultWrapper<String> removeFriend(String selfID, String friendID) {
        return ResultWrapper.wrap(removeFriendUseCase.removeFriend(selfID, friendID));
    }

    public boolean checkIfFriends(String userOne, String userTwo) {
        return checkIfFriendsUseCase.execute(userOne, userTwo);
    }

    public ResultWrapper<?> getFriends(String userID) {
        return getFriendsUseCase.getFriends(userID);
    }

    public ResultWrapper<?> getFriendRequests(String userID) {
        return getFriendsUseCase.getFriendRequests(userID);
    }

}
