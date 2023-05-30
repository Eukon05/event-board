package pl.eukon05.eventboard.user.adapter.out.persistence;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "AppUser")
@AllArgsConstructor
@NoArgsConstructor
class UserEntity {

    @Id
    String id;

    @ElementCollection
    Set<Integer> hostedEventsIDs;

    @ElementCollection
    Set<Integer> participatingEventsIDs;

    @ElementCollection
    Set<Integer> likedEventsIDs;

}
