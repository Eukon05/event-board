package pl.eukon05.eventboard.user.adapter.out.persistence;

import org.mapstruct.Mapper;
import pl.eukon05.eventboard.user.domain.User;

@Mapper(componentModel = "spring")
interface UserEntityMapper {
    User mapEntityToDomain(UserEntity entity);

    UserEntity mapDomainToEntity(User domain);
}
