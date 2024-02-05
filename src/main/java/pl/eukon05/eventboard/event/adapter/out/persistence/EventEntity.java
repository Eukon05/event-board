package pl.eukon05.eventboard.event.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.eukon05.eventboard.event.domain.EventType;

import java.time.LocalDate;
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
    private Long id;

    private String organizerID;
    private String name;
    private String description;

    @Embedded
    private LocationEntity location;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @ElementCollection
    private Set<String> guestIDs;

    @ElementCollection
    private Set<String> likedIDs;
}
