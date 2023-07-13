package pl.eukon05.eventboard.event.adapter.out.persistence;

import org.springframework.data.jpa.domain.Specification;
import pl.eukon05.eventboard.event.domain.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class EventSpecs {

    private EventSpecs() {
    }

    private static Specification<EventEntity> isPublic() {
        return (root, query, cb) -> cb.equal(root.get(EventEntity_.TYPE), EventType.PUBLIC);
    }

    private static Specification<EventEntity> hostedBy(String authorID) {
        return (root, query, cb) -> cb.equal(root.get(EventEntity_.ORGANIZER_ID), authorID);
    }

    private static Specification<EventEntity> hostedInCountry(String country) {
        return (root, query, cb) -> cb.equal(root.get(EventEntity_.LOCATION).get(LocationEntity_.COUNTRY), country);
    }

    private static Specification<EventEntity> hostedInCity(String city) {
        return (root, query, cb) -> cb.equal(root.get(EventEntity_.LOCATION).get(LocationEntity_.CITY), city);
    }

    private static Specification<EventEntity> nameContaining(String name) {
        return (root, query, cb) -> cb.like(root.get(EventEntity_.NAME), name);
    }

    private static Specification<EventEntity> starts(String date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(EventEntity_.START_DATE), date);
    }

    private static Specification<EventEntity> ends(String date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(EventEntity_.END_DATE), date);
    }

    static Specification<EventEntity> build(Map<String, String> parameters) {
        final List<Specification<EventEntity>> specs = new ArrayList<>();

        specs.add(isPublic());

        if (parameters.containsKey(EventEntity_.ORGANIZER_ID))
            specs.add(hostedBy(parameters.get(EventEntity_.ORGANIZER_ID)));

        if (parameters.containsKey(LocationEntity_.COUNTRY))
            specs.add(hostedInCountry(parameters.get(LocationEntity_.COUNTRY)));

        if (parameters.containsKey(LocationEntity_.CITY)) specs.add(hostedInCity(parameters.get(LocationEntity_.CITY)));

        if (parameters.containsKey(EventEntity_.NAME)) specs.add(nameContaining(parameters.get(EventEntity_.NAME)));

        if (parameters.containsKey(EventEntity_.START_DATE)) specs.add(starts(parameters.get(EventEntity_.START_DATE)));

        if (parameters.containsKey(EventEntity_.END_DATE)) specs.add(ends(parameters.get(EventEntity_.END_DATE)));

        return specs.stream().reduce(Specification::and).orElse(null);
    }

}
