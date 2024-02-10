package pl.eukon05.eventboard.invite.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Invite", description = "Operations related to invites")
class InviteController {
    private final InviteFacade facade;

    @PostMapping
    @Operation(summary = "Create a new invite", description = "Creates a new invite, if possible.")
    ResponseEntity<ResultWrapper<String>> createInvite(Principal principal, @RequestBody CreateInviteCommand command) {
        ResultWrapper<String> result = facade.createInvite(principal.getName(), command);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @DeleteMapping("/{eventId}")
    @Operation(summary = "Delete an invite", description = "Deletes an invite, if it's addressed to the current user.")
    @Parameter(description = "ID of the event", name = "eventId", required = true, example = "1")
    ResponseEntity<ResultWrapper<String>> deleteInvite(Principal principal, @PathVariable long eventId) {
        ResultWrapper<String> result = facade.deleteInvite(principal.getName(), eventId);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping
    @Operation(summary = "Get invites", description = "Returns a list of invites addressed to the current user.")
    ResponseEntity<ResultWrapper<List<Invite>>> getInvites(Principal principal) {
        ResultWrapper<List<Invite>> result = facade.getInvites(principal.getName());
        return ResponseEntity.status(result.getStatus()).body(result);
    }

}
