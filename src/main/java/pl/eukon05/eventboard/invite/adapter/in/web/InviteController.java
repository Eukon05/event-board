package pl.eukon05.eventboard.invite.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.invite.application.port.in.command.CreateInviteCommand;
import pl.eukon05.eventboard.invite.application.service.InviteFacade;

import java.security.Principal;

@RestController
@RequestMapping("/invite")
@RequiredArgsConstructor
class InviteController {
    private final InviteFacade facade;

    @PostMapping
    ResponseEntity<ResultWrapper> createInvite(Principal principal, @RequestBody CreateInviteCommand command) {
        ResultWrapper result = facade.createInvite(principal.getName(), command);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @DeleteMapping("/{eventId}")
    ResponseEntity<ResultWrapper> deleteInvite(Principal principal, @PathVariable long eventId) {
        ResultWrapper result = facade.deleteInvite(principal.getName(), eventId);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping
    ResponseEntity<ResultWrapper> getInvites(Principal principal) {
        ResultWrapper result = facade.getInvites(principal.getName());
        return ResponseEntity.status(result.getStatus()).body(result);
    }

}
