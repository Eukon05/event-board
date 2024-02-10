package pl.eukon05.eventboard.user.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.openapi.annotation.param.UUIDParam;
import pl.eukon05.eventboard.user.application.service.UserFacade;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Operations related to friends")
class UserController {

    private final UserFacade facade;

    @PostMapping("/{id}/befriend")
    @Operation(summary = "Send a friend request", description = "Sends a friend request to the user with the given id.")
    @UUIDParam
    ResponseEntity<ResultWrapper<String>> befriendUser(Principal principal, @PathVariable String id) {
        ResultWrapper<String> result = facade.createFriendRequest(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/accept")
    @Operation(summary = "Accept a friend request", description = "Accepts a friend request from the user with the given id.")
    @UUIDParam
    ResponseEntity<ResultWrapper<String>> acceptFriendRequest(Principal principal, @PathVariable String id) {
        ResultWrapper<String> result = facade.acceptFriendRequest(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/reject")
    @Operation(summary = "Reject a friend request", description = "Rejects a friend request from the user with the given id.")
    @UUIDParam
    ResponseEntity<ResultWrapper<String>> rejectFriendRequest(Principal principal, @PathVariable String id) {
        ResultWrapper<String> result = facade.rejectFriendRequest(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/defriend")
    @Operation(summary = "De-friend a user", description = "Removes the user with the given id from the list of friends.")
    @UUIDParam
    ResponseEntity<ResultWrapper<String>> defriendUser(Principal principal, @PathVariable String id) {
        ResultWrapper<String> result = facade.removeFriend(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/friends")
    @Operation(summary = "Get friends", description = "Returns a list of friends of the current user.")
    ResponseEntity<ResultWrapper<?>> getFriends(Principal principal) {
        ResultWrapper<?> result = facade.getFriends(principal.getName());
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/friends/requests")
    @Operation(summary = "Get friend requests", description = "Returns a list of friend requests addressed to the current user.")
    ResponseEntity<ResultWrapper<?>> getFriendRequests(Principal principal) {
        ResultWrapper<?> result = facade.getFriendRequests(principal.getName());
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
