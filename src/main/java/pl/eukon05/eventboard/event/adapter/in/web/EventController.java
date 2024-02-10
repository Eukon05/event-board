package pl.eukon05.eventboard.event.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import pl.eukon05.eventboard.openapi.annotation.param.IDParam;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

import static pl.eukon05.eventboard.common.ResultWrapper.CREATED_RESOURCE_ID;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Tag(name = "Event", description = "Operations related to events")
class EventController {
    private final EventFacade facade;

    @PostMapping
    @Operation(summary = "Create a new event", description = "Creates a new event and returns its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created"),
            @ApiResponse(responseCode = "400", description = "Validation of request body failed"),
    })
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
    @Operation(summary = "Make an event public", description = "If the current user is the organizer of the event, it will be made public (if not public already).")
    @IDParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event published"),
            @ApiResponse(responseCode = "403", description = "User is not the organizer of the event"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
    })
    ResponseEntity<ResultWrapper<String>> publishEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<String> result = facade.publishEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/unpublish")
    @Operation(summary = "Make an event private", description = "If the current user is the organizer of the event, it will be made private (if not private already).")
    @IDParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event un-published"),
            @ApiResponse(responseCode = "403", description = "User is not the organizer of the event"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
    })
    ResponseEntity<ResultWrapper<String>> unpublishEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<String> result = facade.unpublishEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/attend")
    @Operation(summary = "Attend an event", description = "Adds the current user to the list of attendees of the event, if possible.")
    @IDParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User added to the list of attendees"),
            @ApiResponse(responseCode = "400", description = "User is already attending the event"),
            @ApiResponse(responseCode = "403", description = "The event is private"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
    })
    ResponseEntity<ResultWrapper<String>> attendEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<String> result = facade.attendEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/{id}/unattend")
    @Operation(summary = "Un-attend an event", description = "Removes the current user from the list of attendees of the event, if possible.")
    @IDParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User removed from the list of attendees"),
            @ApiResponse(responseCode = "400", description = "User is not attending the event"),
            @ApiResponse(responseCode = "403", description = "The event is private"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
    })
    ResponseEntity<ResultWrapper<String>> unattendEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<String> result = facade.unattendEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an event", description = "If the current user is the organizer of the event, deletes the event.")
    @IDParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event deleted"),
            @ApiResponse(responseCode = "403", description = "User is not the organizer of the event"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
    })
    ResponseEntity<ResultWrapper<String>> deleteEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<String> result = facade.deleteEvent(principal.getName(), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Edit an event", description = "If the current user is the organizer of the event, edits the event.")
    @IDParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event edited"),
            @ApiResponse(responseCode = "400", description = "Validation of request body failed"),
            @ApiResponse(responseCode = "403", description = "User is not the organizer of the event"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
    })
    ResponseEntity<ResultWrapper<String>> modifyEvent(Principal principal, @PathVariable long id, @RequestBody @Valid ModifyEventCommand command) {
        ResultWrapper<String> result = facade.modifyEvent(principal.getName(), id, command);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping
    @Operation(summary = "Search for events", description = "Looks up events based on the provided parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events found"),
            @ApiResponse(responseCode = "400", description = "Validation of request parameters failed"),
    })
    ResponseEntity<ResultWrapper<Page<EventDTO>>> search(@ParameterObject Pageable pageable, @RequestParam(required = false) Map<String, String> params) {
        ResultWrapper<Page<EventDTO>> result = facade.searchForEvent(params, pageable);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get an event", description = "Returns the event with the given id, if it exists and is accessible to the current user.")
    @IDParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "403", description = "The event is private"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
    })
    ResponseEntity<ResultWrapper<?>> getEvent(Principal principal, @PathVariable long id) {
        ResultWrapper<?> wrapper = facade.getEvent(principal.getName(), id);
        return ResponseEntity.status(wrapper.getStatus()).body(wrapper);
    }

    @GetMapping("/attended")
    @Operation(summary = "Get events attended by the current user", description = "Returns a list of events attended by the current user.")
    @ApiResponse(responseCode = "200", description = "Events found")
    ResponseEntity<ResultWrapper<Page<EventDTO>>> getAttended(Principal principal, @ParameterObject Pageable pageable) {
        ResultWrapper<Page<EventDTO>> result = facade.searchForAttended(principal.getName(), pageable);
        return ResponseEntity.status(result.getStatus()).body(result);
    }


    @GetMapping("/organized")
    @Operation(summary = "Get events organized by the current user", description = "Returns a list of events organized by the current user.")
    @ApiResponse(responseCode = "200", description = "Events found")
    ResponseEntity<ResultWrapper<Page<EventDTO>>> getOrganized(Principal principal, @ParameterObject Pageable pageable) {
        ResultWrapper<Page<EventDTO>> result = facade.searchForOrganized(principal.getName(), pageable);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
