package pl.eukon05.eventboard.user.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
class UserPersistenceAdapter implements GetUserPort, SaveUserPort {

    private final UserEntityMapper mapper;
    private final UserRepository repository;

    @Override
    public Optional<User> getUserById(String id) {
        return repository.findById(id).map(mapper::mapEntityToDomain);
    }

    @Override
    public void saveUser(User user) {
        UserEntity entity = mapper.mapDomainToEntity(user);
        repository.save(entity);
    }
}
