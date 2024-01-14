package pl.eukon05.eventboard.invite.application.service;

import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.invite.application.port.out.CheckEventGettableOutPort;
import pl.eukon05.eventboard.invite.application.port.out.CheckIfFriendsOutPort;
import pl.eukon05.eventboard.invite.application.port.out.CheckUserHostOutPort;
import pl.eukon05.eventboard.invite.application.port.out.CheckUserInvitedOutPort;

final class InviteUnitTestUtils {
    static final String userID = "someid";
    static final String friendID = "somefriendid";
    
    static void checkingInvitedWillReturn(CheckUserInvitedOutPort checkUserInvitedOutPort, boolean val) {
        Mockito.when(checkUserInvitedOutPort.checkUserInvited(friendID, 1L)).thenReturn(val);
    }

    static void checkingFriendsWillReturn(CheckIfFriendsOutPort checkIfFriendsOutPort, boolean val) {
        Mockito.when(checkIfFriendsOutPort.checkIfFriends(userID, friendID)).thenReturn(val);
    }

    static void checkingHostWillReturn(CheckUserHostOutPort checkUserHostOutPort, boolean val) {
        Mockito.when(checkUserHostOutPort.checkUserHost(friendID, 1L)).thenReturn(val);
    }

    static void checkingGettableWillReturn(CheckEventGettableOutPort checkEventGettableOutPort, Result result) {
        Mockito.when(checkEventGettableOutPort.checkEventGettable(userID, 1L)).thenReturn(result);
    }
}
