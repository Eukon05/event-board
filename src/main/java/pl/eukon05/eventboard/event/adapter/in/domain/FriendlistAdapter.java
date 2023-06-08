package pl.eukon05.eventboard.event.adapter.in.domain;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.event.application.port.in.GetUserFriendlistPort;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.domain.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Adapter
@RequiredArgsConstructor
class FriendlistAdapter implements GetUserFriendlistPort {
    private final GetUserPort getUserPort;

    @Override
    public List<String> getUserFriendlist(String userID) {
        List<String> friendlist = Collections.emptyList();

        Optional<User> user = getUserPort.getUserById(userID);
        if (user.isPresent())
            friendlist = user.get().getFriends().stream().map(User::getId).toList();

        return friendlist;
    }
}
