package pl.eukon05.eventboard.security.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import pl.eukon05.eventboard.security.application.port.out.CreateUserPort;


@Component
@RequiredArgsConstructor
class UserRegistrationHandler {
    private final CreateUserPort createUserPort;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        createUserPort.createUser(success.getAuthentication().getName());
    }
}
