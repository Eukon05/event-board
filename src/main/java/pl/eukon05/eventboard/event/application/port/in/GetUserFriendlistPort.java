package pl.eukon05.eventboard.event.application.port.in;

import java.util.List;

public interface GetUserFriendlistPort {
    List<String> getUserFriendlist(String userID);
}
