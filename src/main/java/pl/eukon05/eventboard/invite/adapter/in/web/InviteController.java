package pl.eukon05.eventboard.invite.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.invite.application.port.in.command.CreateInviteCommand;
import pl.eukon05.eventboard.invite.application.service.InviteFacade;
import pl.eukon05.eventboard.invite.domain.Invite;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/invite")
@RequiredArgsConstructor
class InviteController {
    private final InviteFacade facade;

    @PostMapping
    ResponseEntity<ResultWrapper<String>> createInvite(Principal principal, @RequestBody CreateInviteCommand command) {
        ResultWrapper<String> result = facade.createInvite(principal.getName(), command);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @DeleteMapping("/{eventId}")
    ResponseEntity<ResultWrapper<String>> deleteInvite(Principal principal, @PathVariable long eventId) {
        ResultWrapper<String> result = facade.deleteInvite(principal.getName(), eventId);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping
    ResponseEntity<ResultWrapper<List<Invite>>> getInvites(Principal principal) {
        ResultWrapper<List<Invite>> result = facade.getInvites(principal.getName());
        return ResponseEntity.status(result.getStatus()).body(result);
    }

}
