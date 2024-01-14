package pl.eukon05.eventboard.invite.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.invite.application.port.out.CheckUserInvitedOutPort;
import pl.eukon05.eventboard.invite.application.port.out.DeleteInvitePort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.eukon05.eventboard.invite.application.service.InviteUnitTestUtils.checkingInvitedWillReturn;
import static pl.eukon05.eventboard.invite.application.service.InviteUnitTestUtils.friendID;

class DeleteInviteUnitTests {
    private final CheckUserInvitedOutPort checkInvitedPort = Mockito.mock(CheckUserInvitedOutPort.class);
    private final DeleteInvitePort deletePort = Mockito.mock(DeleteInvitePort.class);

    private final DeleteInviteUseCase useCase = new DeleteInviteUseCase(checkInvitedPort, deletePort);

    @Test
    void should_delete_invite() {
        checkingInvitedWillReturn(checkInvitedPort, true);
        Result res = useCase.deleteInvite(friendID, 1L);

        assertEquals(Result.SUCCESS, res);
        Mockito.verify(deletePort).deleteInvite(friendID, 1L);
    }

    @Test
    void should_not_delete_non_existent_invite() {
        checkingInvitedWillReturn(checkInvitedPort, false);
        Result res = useCase.deleteInvite(friendID, 1L);

        assertEquals(Result.INVITE_NOT_FOUND, res);
    }
}
