package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.dto.EventDTO;
import pl.eukon05.eventboard.event.application.port.out.dto.EventDTOMapper;

import java.util.Map;

@UseCase
@RequiredArgsConstructor
class SearchForEventUseCase {

    private final GetEventPort getEventPort;
    private final EventDTOMapper mapper;

    Page<EventDTO> execute(Map<String, String> parameters, Pageable pageable) {
        return getEventPort.search(parameters, pageable).map(mapper::mapDomainToDTO);
    }

}
