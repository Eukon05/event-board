package pl.eukon05.eventboard.user.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.user.application.service.UserFacade;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserController {

    private final UserFacade facade;

    @PostMapping("/{id}/befriend")
    ResponseEntity<String> befriendUser(Principal principal, @PathVariable String id) {
        Result result = facade.createFriendRequest(principal.getName(), id);
        return new ResponseEntity<>(result.getMessage(), result.getStatus());
    }

    @PostMapping("/{id}/accept")
    ResponseEntity<String> acceptFriendRequest(Principal principal, @PathVariable String id) {
        Result result = facade.acceptFriendRequest(principal.getName(), id);
        return new ResponseEntity<>(result.getMessage(), result.getStatus());
    }

    @PostMapping("/{id}/reject")
    ResponseEntity<String> rejectFriendRequest(Principal principal, @PathVariable String id) {
        Result result = facade.rejectFriendRequest(principal.getName(), id);
        return new ResponseEntity<>(result.getMessage(), result.getStatus());
    }

    @PostMapping("/{id}/defriend")
    ResponseEntity<String> defriendUser(Principal principal, @PathVariable String id) {
        Result result = facade.removeFriend(principal.getName(), id);
        return new ResponseEntity<>(result.getMessage(), result.getStatus());
    }

    @GetMapping("/friends")
    ResponseEntity<Object> getFriends(Principal principal) {
        ResultWrapper result = facade.getFriends(principal.getName());
        return ResponseEntity.status(result.getResult().getStatus()).body(result.getContent());
    }

    @GetMapping("/friends/requests")
    ResponseEntity<Object> getFriendRequests(Principal principal) {
        ResultWrapper result = facade.getFriendRequests(principal.getName());
        return ResponseEntity.status(result.getResult().getStatus()).body(result.getContent());
    }
}
