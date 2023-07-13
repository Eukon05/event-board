package pl.eukon05.eventboard.event.application.port.out.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.eukon05.eventboard.event.domain.Event;

@Mapper(componentModel = "spring")
public interface EventDTOMapper {

    @Mapping(target = "guestCount", expression = "java(domain.getGuestIDs().size())")
    @Mapping(target = "inviteesCount", expression = "java(domain.getInviteeIDs().size())")
    @Mapping(target = "likesCount", expression = "java(domain.getLikedIDs().size())")
    EventDTO mapDomainToDTO(Event domain);

}
