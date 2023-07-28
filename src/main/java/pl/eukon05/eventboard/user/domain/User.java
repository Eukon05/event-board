package pl.eukon05.eventboard.user.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Result;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class User {

    private final String id;
    private final Set<String> friendIDs = new HashSet<>();
    private final Set<String> friendRequestIDs = new HashSet<>();

    public Result addFriendRequest(User friend) {
        String friendID = friend.getId();

        if (friendIDs.contains(friendID) || friend.getFriendIDs().contains(id))
            return Result.USER_ALREADY_FRIEND;

        if (friendRequestIDs.contains(friendID))
            return Result.FRIEND_REQUEST_ALREADY_SENT;

        friendRequestIDs.add(friendID);

        return Result.SUCCESS;
    }

    public Result acceptFriendRequest(User friend) {
        String friendID = friend.getId();

        if (!friendRequestIDs.contains(friendID)) return Result.FRIEND_NOT_REQUESTED;

        addFriendID(friendID);
        friend.addFriendID(id);

        return Result.SUCCESS;
    }

    public Result rejectFriendRequest(String friendID) {
        if (!friendRequestIDs.contains(friendID)) return Result.FRIEND_NOT_REQUESTED;
        friendRequestIDs.remove(friendID);

        return Result.SUCCESS;
    }

    private void addFriendID(String friendID) {
        friendRequestIDs.remove(friendID);
        friendIDs.add(friendID);
    }

    public Result removeFriend(User friend) {
        String friendID = friend.getId();

        if (!friendIDs.contains(friendID))
            return Result.USER_NOT_FRIEND;

        removeFriendID(friendID);
        friend.removeFriendID(id);

        return Result.SUCCESS;
    }

    private void removeFriendID(String friendID) {
        friendIDs.remove(friendID);
    }

}
