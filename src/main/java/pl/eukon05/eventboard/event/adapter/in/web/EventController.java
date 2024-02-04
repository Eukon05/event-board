package pl.eukon05.eventboard.event.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.event.application.port.in.command.CreateEventCommand;
import pl.eukon05.eventboard.event.application.port.in.command.ModifyEventCommand;
import pl.eukon05.eventboard.event.application.port.out.dto.EventDTO;
import pl.eukon05.eventboard.event.application.service.EventFacade;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

import static pl.eukon05.eventboard.common.ResultWrapper.CREATED_RESOURCE_ID;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
class EventController {
    private final EventFacade facade;

    @PostMapping
    ResponseEntity<ResultWrapper<String>> createEvent(Principal principal, @RequestBody @Valid CreateEventCommand dto) {
        ResultWrapper<String> result = facade.createEvent(principal.getName(), dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getDetails().get(CREATED_RESOURCE_ID))
                .toUri();

        return ResponseEntity.created(location).body(result);
    }

    @PostMapping("/{id}/publish")
    ResponseEntity<ResultWrapper<String>> publishEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<String> result = facade.publishEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/unpublish")
    ResponseEntity<ResultWrapper<String>> unpublishEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<String> result = facade.unpublishEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/attend")
    ResponseEntity<ResultWrapper<String>> attendEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<String> result = facade.attendEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/unattend")
    ResponseEntity<ResultWrapper<String>> unattendEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<String> result = facade.unattendEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResultWrapper<String>> deleteEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<String> result = facade.deleteEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PatchMapping("/{id}")
    ResponseEntity<ResultWrapper<String>> modifyEvent(Principal principal, @PathVariable long id, @RequestBody @Valid ModifyEventCommand command) {
        ResultWrapper<String> result = facade.modifyEvent(principal.getName(), id, command);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping
    ResponseEntity<ResultWrapper<Page<EventDTO>>> search(@ParameterObject Pageable pageable, @RequestParam(required = false) Map<String, String> params) {
        ResultWrapper<Page<EventDTO>> result = facade.searchForEvent(params, pageable);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("{id}")
    ResponseEntity<ResultWrapper<?>> getEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<?> wrapper = facade.getEvent(principal.getName(), id);
        return ResponseEntity.status(wrapper.getStatus()).body(wrapper);
    }

    @GetMapping("/attended")
    ResponseEntity<ResultWrapper<Page<EventDTO>>> getAttended(Principal principal, @ParameterObject Pageable pageable) {
        ResultWrapper<Page<EventDTO>> result = facade.searchForAttended(principal.getName(), pageable);
        return ResponseEntity.status(result.getStatus()).body(result);
    }


    @GetMapping("/organized")
    ResponseEntity<ResultWrapper<Page<EventDTO>>> getOrganized(Principal principal, @ParameterObject Pageable pageable) {
        ResultWrapper<Page<EventDTO>> result = facade.searchForOrganized(principal.getName(), pageable);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
