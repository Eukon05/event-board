package pl.eukon05.eventboard.security.adapter.out.user;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.security.application.port.out.CreateUserOutPort;
import pl.eukon05.eventboard.user.application.port.in.CreateUserInPort;

@Adapter
@RequiredArgsConstructor
class SecurityUserOutAdapter implements CreateUserOutPort {
    private final CreateUserInPort createUserInPort;

    @Override
    public void createUser(String id) {
        createUserInPort.createUser(id);
    }
}
