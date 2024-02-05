package pl.eukon05.eventboard.invite.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.invite.application.port.in.command.CreateInviteCommand;
import pl.eukon05.eventboard.invite.application.port.in.command.InviteCommandMapper;
import pl.eukon05.eventboard.invite.application.port.out.*;
import pl.eukon05.eventboard.invite.domain.Invite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.eukon05.eventboard.invite.application.service.InviteUnitTestUtils.*;

class CreateInviteUnitTests {
    private final SaveInvitePort savePort = Mockito.mock(SaveInvitePort.class);
    private final CheckIfFriendsOutPort checkFriendsPort = Mockito.mock(CheckIfFriendsOutPort.class);
    private final CheckUserHostOutPort checkHostPort = Mockito.mock(CheckUserHostOutPort.class);
    private final CheckUserInvitedOutPort checkInvitedPort = Mockito.mock(CheckUserInvitedOutPort.class);
    private final CheckEventGettableOutPort checkGettablePort = Mockito.mock(CheckEventGettableOutPort.class);
    private final CheckUserAttendeeOutPort checkAttendeePort = Mockito.mock(CheckUserAttendeeOutPort.class);
    private final InviteCommandMapper mapper = Mockito.mock(InviteCommandMapper.class);

    private final CreateInviteUseCase useCase = new CreateInviteUseCase(mapper, savePort, checkFriendsPort, checkInvitedPort, checkGettablePort, checkHostPort, checkAttendeePort);
    private static final CreateInviteCommand command = new CreateInviteCommand(friendID, 1L);
    private static final Invite mappingResult = new Invite(friendID, 1L);

    @Test
    void should_create_invite() {
        mappingWillReturn();
        checkingFriendsWillReturn(checkFriendsPort, true);
        checkingInvitedWillReturn(checkInvitedPort, false);
        checkingHostWillReturn(checkHostPort, false);
        checkingAttendeeWillReturn(checkAttendeePort, false);
        checkingGettableWillReturn(checkGettablePort, Result.SUCCESS);

        Result res = useCase.createInvite(userID, command);

        Mockito.verify(mapper).mapCreateCommandToDomain(command);
        Mockito.verify(savePort).saveInvite(mappingResult);
        assertEquals(Result.SUCCESS, res);
    }

    @Test
    void should_not_invite_host() {
        mappingWillReturn();
        checkingFriendsWillReturn(checkFriendsPort, true);
        checkingInvitedWillReturn(checkInvitedPort, false);
        checkingHostWillReturn(checkHostPort, true);
        checkingAttendeeWillReturn(checkAttendeePort, false);
        checkingGettableWillReturn(checkGettablePort, Result.SUCCESS);

        Result res = useCase.createInvite(userID, command);
        assertEquals(Result.INVITE_ORGANIZER, res);
    }

    @Test
    void should_not_invite_non_friend() {
        mappingWillReturn();
        checkingFriendsWillReturn(checkFriendsPort, false);
        checkingInvitedWillReturn(checkInvitedPort, false);
        checkingHostWillReturn(checkHostPort, false);
        checkingAttendeeWillReturn(checkAttendeePort, false);
        checkingGettableWillReturn(checkGettablePort, Result.SUCCESS);

        Result res = useCase.createInvite(userID, command);
        assertEquals(Result.USER_NOT_FRIEND, res);
    }

    @Test
    void should_not_invite_already_invited() {
        mappingWillReturn();
        checkingFriendsWillReturn(checkFriendsPort, true);
        checkingInvitedWillReturn(checkInvitedPort, true);
        checkingHostWillReturn(checkHostPort, false);
        checkingAttendeeWillReturn(checkAttendeePort, false);
        checkingGettableWillReturn(checkGettablePort, Result.SUCCESS);

        Result res = useCase.createInvite(userID, command);
        assertEquals(Result.USER_ALREADY_INVITEE, res);
    }

    @Test
    void should_not_invite_already_attending() {
        mappingWillReturn();
        checkingFriendsWillReturn(checkFriendsPort, true);
        checkingInvitedWillReturn(checkInvitedPort, false);
        checkingHostWillReturn(checkHostPort, false);
        checkingAttendeeWillReturn(checkAttendeePort, true);
        checkingGettableWillReturn(checkGettablePort, Result.SUCCESS);

        Result res = useCase.createInvite(userID, command);
        assertEquals(Result.USER_ALREADY_ATTENDEE, res);
    }

    @Test
    void should_not_invite_to_inaccessible_event() {
        mappingWillReturn();
        checkingFriendsWillReturn(checkFriendsPort, true);
        checkingInvitedWillReturn(checkInvitedPort, false);
        checkingHostWillReturn(checkHostPort, false);
        checkingGettableWillReturn(checkGettablePort, Result.EVENT_PRIVATE);

        Result res = useCase.createInvite(userID, command);
        assertEquals(Result.EVENT_PRIVATE, res);
    }

    private void mappingWillReturn() {
        Mockito.when(mapper.mapCreateCommandToDomain(command)).thenReturn(mappingResult);
    }
}
