package pl.eukon05.eventboard.event.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.event.application.port.in.command.CreateEventCommand;
import pl.eukon05.eventboard.event.application.port.in.command.ModifyEventCommand;
import pl.eukon05.eventboard.event.application.service.EventFacade;

import java.security.Principal;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
class EventController {
    private final EventFacade facade;

    @PostMapping
    ResponseEntity<String> createEvent(Principal principal, @RequestBody @Valid CreateEventCommand dto) {
        Result result = facade.createEvent(principal.getName(), dto);
        return ResponseEntity.status(result.getStatus()).body(result.getMessage());
    }

    @PostMapping("/{id}/publish")
    ResponseEntity<String> publishEvent(Principal principal, @PathVariable long id) {
        Result result = facade.publishEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result.getMessage());
    }

    @PostMapping("/{id}/invite")
    ResponseEntity<String> inviteToEvent(Principal principal, @PathVariable long id, @RequestParam String friendID) {
        Result result = facade.inviteToEvent(principal.getName(), friendID, id);
        return ResponseEntity.status(result.getStatus()).body(result.getMessage());
    }

    @PostMapping("/{id}/attend")
    ResponseEntity<String> attendEvent(Principal principal, @PathVariable long id) {
        Result result = facade.attendEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result.getMessage());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteEvent(Principal principal, @PathVariable long id) {
        Result result = facade.deleteEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result.getMessage());
    }

    @PatchMapping("/{id}")
    ResponseEntity<String> modifyEvent(Principal principal, @PathVariable long id, @RequestBody @Valid ModifyEventCommand command) {
        Result result = facade.modifyEvent(principal.getName(), id, command);
        return ResponseEntity.status(result.getStatus()).body(result.getMessage());
    }

}
