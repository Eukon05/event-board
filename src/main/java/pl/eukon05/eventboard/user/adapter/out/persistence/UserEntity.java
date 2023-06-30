package pl.eukon05.eventboard.user.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "AppUser")
@NoArgsConstructor
@Getter
@Setter
class UserEntity {
    @Id
    private String id;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> friendIDs;
}
