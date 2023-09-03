package pl.eukon05.eventboard.user.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.user.application.service.UserFacade;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserController {

    private final UserFacade facade;

    @PostMapping("/{id}/befriend")
    ResponseEntity<ResultWrapper> befriendUser(Principal principal, @PathVariable String id) {
        ResultWrapper result = facade.createFriendRequest(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/accept")
    ResponseEntity<ResultWrapper> acceptFriendRequest(Principal principal, @PathVariable String id) {
        ResultWrapper result = facade.acceptFriendRequest(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/reject")
    ResponseEntity<ResultWrapper> rejectFriendRequest(Principal principal, @PathVariable String id) {
        ResultWrapper result = facade.rejectFriendRequest(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/defriend")
    ResponseEntity<ResultWrapper> defriendUser(Principal principal, @PathVariable String id) {
        ResultWrapper result = facade.removeFriend(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/friends")
    ResponseEntity<ResultWrapper> getFriends(Principal principal) {
        ResultWrapper result = facade.getFriends(principal.getName());
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/friends/requests")
    ResponseEntity<ResultWrapper> getFriendRequests(Principal principal) {
        ResultWrapper result = facade.getFriendRequests(principal.getName());
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
