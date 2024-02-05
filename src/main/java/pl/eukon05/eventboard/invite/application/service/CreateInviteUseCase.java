package pl.eukon05.eventboard.invite.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.invite.application.port.in.command.CreateInviteCommand;
import pl.eukon05.eventboard.invite.application.port.in.command.InviteCommandMapper;
import pl.eukon05.eventboard.invite.application.port.out.*;
import pl.eukon05.eventboard.invite.domain.Invite;

@UseCase
@RequiredArgsConstructor
class CreateInviteUseCase {
    private final InviteCommandMapper mapper;
    private final SaveInvitePort saveInvitePort;
    private final CheckIfFriendsOutPort checkIfFriendsOutPort;
    private final CheckUserInvitedOutPort checkUserInvitedOutPort;
    private final CheckEventGettableOutPort checkEventGettableOutPort;
    private final CheckUserHostOutPort checkUserHostOutPort;
    private final CheckUserAttendeeOutPort checkUserAttendeeOutPort;

    Result createInvite(String userId, CreateInviteCommand command) {
        if (userId.equals(command.inviteeId())) return Result.INVITE_SELF;

        if (!checkIfFriendsOutPort.checkIfFriends(userId, command.inviteeId()))
            return Result.USER_NOT_FRIEND;

        Result getEventResult = checkEventGettableOutPort.checkEventGettable(userId, command.eventId());

        if (!getEventResult.equals(Result.SUCCESS))
            return getEventResult;

        if (checkUserHostOutPort.checkUserHost(command.inviteeId(), command.eventId()))
            return Result.INVITE_ORGANIZER;

        if (checkUserInvitedOutPort.checkUserInvited(command.inviteeId(), command.eventId()))
            return Result.USER_ALREADY_INVITEE;

        if (checkUserAttendeeOutPort.checkUserAttendee(command.inviteeId(), command.eventId()))
            return Result.USER_ALREADY_ATTENDEE;

        Invite invite = mapper.mapCreateCommandToDomain(command);
        saveInvitePort.saveInvite(invite);

        return Result.SUCCESS;
    }
}
