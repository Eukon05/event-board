package pl.eukon05.eventboard.event.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.event.application.port.in.command.CreateEventCommand;
import pl.eukon05.eventboard.event.application.port.in.command.ModifyEventCommand;
import pl.eukon05.eventboard.event.application.port.out.dto.EventDTO;
import pl.eukon05.eventboard.event.application.service.EventFacade;

import java.security.Principal;
import java.util.Map;

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

    @PostMapping("/{id}/unattend")
    ResponseEntity<String> unattendEvent(Principal principal, @PathVariable long id) {
        Result result = facade.unattendEvent(principal.getName(), id);
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

    @GetMapping
    ResponseEntity<Page<EventDTO>> search(@ParameterObject Pageable pageable, @RequestParam(required = false) Map<String, String> params) {
        Page<EventDTO> page = facade.searchForEvent(params, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("{id}")
    ResponseEntity<Object> getEvent(Principal principal, @PathVariable long id) {
        ResultWrapper wrapper = facade.getEvent(principal.getName(), id);
        return ResponseEntity.status(wrapper.getResult().getStatus()).body(wrapper.getContent());
    }

    @GetMapping("/attended")
    ResponseEntity<Object> getAttended(Principal principal, @ParameterObject Pageable pageable) {
        Page<EventDTO> page = facade.searchForAttended(principal.getName(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/invited")
    ResponseEntity<Object> getInvited(Principal principal, @ParameterObject Pageable pageable) {
        Page<EventDTO> page = facade.searchForInvited(principal.getName(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/organized")
    ResponseEntity<Object> getOrganized(Principal principal, @ParameterObject Pageable pageable) {
        Page<EventDTO> page = facade.searchForOrganized(principal.getName(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
