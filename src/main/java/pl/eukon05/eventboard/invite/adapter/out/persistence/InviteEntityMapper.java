package pl.eukon05.eventboard.invite.adapter.out.persistence;

import org.mapstruct.Mapper;
import pl.eukon05.eventboard.invite.domain.Invite;

@Mapper(componentModel = "spring")
interface InviteEntityMapper {
    InviteEntity mapDomainToEntity(Invite invite);

    Invite mapEntityToDomain(InviteEntity entity);
}
