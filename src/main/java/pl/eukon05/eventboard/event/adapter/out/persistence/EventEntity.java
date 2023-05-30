package pl.eukon05.eventboard.event.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.eukon05.eventboard.event.domain.EventType;

import java.util.Set;

@Entity
@Table(name = "event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String organizerID;
    private String name;
    private String description;

    @Embedded
    private LocationEntity location;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @ElementCollection
    private Set<Integer> guestIDs;

    @ElementCollection
    private Set<Integer> inviteeIDs;
}
