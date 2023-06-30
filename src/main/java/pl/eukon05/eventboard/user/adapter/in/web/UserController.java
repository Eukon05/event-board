package pl.eukon05.eventboard.user.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.user.application.service.UserFacade;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserController {

    private final UserFacade facade;

    @PostMapping("/{id}/befriend")
    ResponseEntity<String> befriendUser(Principal principal, @PathVariable String id) {
        Result result = facade.befriend(principal.getName(), id);
        return new ResponseEntity<>(result.getMessage(), result.getStatus());
    }

    @PostMapping("/{id}/defriend")
    ResponseEntity<String> defriendUser(Principal principal, @PathVariable String id) {
        Result result = facade.defriend(principal.getName(), id);
        return new ResponseEntity<>(result.getMessage(), result.getStatus());
    }
}
