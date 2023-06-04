package pl.eukon05.eventboard.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.eukon05.eventboard.user.application.port.out.CreateUserPort;

import java.io.IOException;
import java.security.Principal;

@Component
@RequiredArgsConstructor
class UserRegistrationFilter extends OncePerRequestFilter {

    private final CreateUserPort createUserPort;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            createUserPort.createUser(principal.getName());
        }

        chain.doFilter(request, response);
    }
}
