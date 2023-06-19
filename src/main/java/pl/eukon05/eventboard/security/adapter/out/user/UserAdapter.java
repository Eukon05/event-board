package pl.eukon05.eventboard.security.adapter.out.user;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.security.application.port.out.CreateUserPort;

@Adapter
@RequiredArgsConstructor
class UserAdapter implements CreateUserPort {
    private final pl.eukon05.eventboard.user.application.port.in.CreateUserPort createUserPort;

    @Override
    public void createUser(String id) {
        createUserPort.createUser(id);
    }
}
