package pl.eukon05.eventboard.invite.application.port.in.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateInviteCommand(@NotNull @NotBlank String inviteeId, @Positive long eventId) {
}
