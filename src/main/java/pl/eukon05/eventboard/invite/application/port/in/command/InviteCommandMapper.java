package pl.eukon05.eventboard.invite.application.port.in.command;

import org.mapstruct.Mapper;
import pl.eukon05.eventboard.invite.domain.Invite;

@Mapper(componentModel = "spring")
public interface InviteCommandMapper {
    Invite mapCreateCommandToDomain(CreateInviteCommand command);
}
