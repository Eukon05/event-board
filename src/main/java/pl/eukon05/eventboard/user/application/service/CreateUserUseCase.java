package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.user.application.port.in.CreateUserPort;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.application.port.out.SaveUserPort;
import pl.eukon05.eventboard.user.domain.User;

@UseCase
@RequiredArgsConstructor
class CreateUserUseCase implements CreateUserPort {
    private final GetUserPort getUserPort;
    private final SaveUserPort saveUserPort;

    public void createUser(String id) {
        if (getUserPort.getUserById(id).isEmpty()) {
            User user = new User(id);
            saveUserPort.saveUser(user);
        }
    }
}
