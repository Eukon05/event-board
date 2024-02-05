package pl.eukon05.eventboard.user.adapter.in;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.user.application.port.in.CreateUserInPort;
import pl.eukon05.eventboard.user.application.service.UserFacade;

@Adapter
@RequiredArgsConstructor
class UserSecurityInAdapter implements CreateUserInPort {
    private final UserFacade facade;

    @Override
    public void createUser(String id) {
        facade.createUser(id);
    }
}
