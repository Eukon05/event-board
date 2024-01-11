package pl.eukon05.eventboard.invite.application.port.in.command;

public record CreateInviteCommand(String inviteeId, long eventId) {
}
