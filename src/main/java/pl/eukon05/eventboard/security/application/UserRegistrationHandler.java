package pl.eukon05.eventboard.security.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import pl.eukon05.eventboard.security.application.port.out.CreateUserOutPort;


@Component
@RequiredArgsConstructor
class UserRegistrationHandler {
    private final CreateUserOutPort createUserOutPort;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        createUserOutPort.createUser(success.getAuthentication().getName());
    }
}
