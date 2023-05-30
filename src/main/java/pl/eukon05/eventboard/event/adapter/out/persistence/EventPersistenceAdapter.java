package pl.eukon05.eventboard.event.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class EventPersistenceAdapter implements GetEventPort, SaveEventPort {

    private final EventRepository repository;
    private final EventEntityMapper mapper;

    @Override
    public Optional<Event> getEventById(long eventID) {
        return repository.findById(eventID).map(mapper::mapEntityToDomain);
    }

    @Override
    public void saveEvent(Event event) {
        repository.save(mapper.mapDomainToEntity(event));
    }
}
