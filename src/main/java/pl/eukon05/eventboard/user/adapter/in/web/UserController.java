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
    ResponseEntity<ResultWrapper<String>> befriendUser(Principal principal, @PathVariable String id) {
        ResultWrapper<String> result = facade.createFriendRequest(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/accept")
    ResponseEntity<ResultWrapper<String>> acceptFriendRequest(Principal principal, @PathVariable String id) {
        ResultWrapper<String> result = facade.acceptFriendRequest(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/reject")
    ResponseEntity<ResultWrapper<String>> rejectFriendRequest(Principal principal, @PathVariable String id) {
        ResultWrapper<String> result = facade.rejectFriendRequest(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/defriend")
    ResponseEntity<ResultWrapper<String>> defriendUser(Principal principal, @PathVariable String id) {
        ResultWrapper<String> result = facade.removeFriend(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/friends")
    ResponseEntity<ResultWrapper<?>> getFriends(Principal principal) {
        ResultWrapper<?> result = facade.getFriends(principal.getName());
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/friends/requests")
    ResponseEntity<ResultWrapper<?>> getFriendRequests(Principal principal) {
        ResultWrapper<?> result = facade.getFriendRequests(principal.getName());
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
