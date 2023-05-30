package pl.eukon05.eventboard.event.adapter.out.persistence;

import org.mapstruct.Mapper;
import pl.eukon05.eventboard.event.domain.Event;

@Mapper(componentModel = "spring")
interface EventEntityMapper {

    Event mapEntityToDomain(EventEntity entity);

    EventEntity mapDomainToEntity(Event domain);

}
