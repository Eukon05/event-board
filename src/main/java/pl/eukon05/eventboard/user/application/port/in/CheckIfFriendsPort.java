package pl.eukon05.eventboard.user.application.port.in;

import pl.eukon05.eventboard.user.domain.User;

public interface CheckIfFriendsPort {
    boolean checkIfFriends(String userOne, String userTwo);

    boolean checkIfFriends(User userOne, User userTwo);
}
