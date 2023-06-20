package pl.eukon05.eventboard.event.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.event.application.port.out.DeleteEventPort;
import pl.eukon05.eventboard.event.application.port.out.GetEventPort;
import pl.eukon05.eventboard.event.application.port.out.SaveEventPort;
import pl.eukon05.eventboard.event.domain.Event;

import java.util.Map;
import java.util.Optional;

@Adapter
@RequiredArgsConstructor
class EventPersistenceAdapter implements GetEventPort, SaveEventPort, DeleteEventPort {

    private final EventRepository repository;
    private final EventEntityMapper mapper;

    @Override
    public Optional<Event> getEventById(long eventID) {
        return repository.findById(eventID).map(mapper::mapEntityToDomain);
    }

    @Override
    public Page<Event> search(Map<String, String> parameters, Pageable pageable) {
        return repository.findAll(EventSpecs.build(parameters), pageable).map(mapper::mapEntityToDomain);
    }

    @Override
    public long saveEvent(Event event) {
        return repository.save(mapper.mapDomainToEntity(event)).getId();
    }

    @Override
    public void deleteEvent(long eventID) {
        repository.deleteById(eventID);
    }
}
