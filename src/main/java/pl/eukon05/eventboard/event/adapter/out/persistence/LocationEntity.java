package pl.eukon05.eventboard.event.adapter.out.persistence;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class LocationEntity {
    private String country;
    private String city;
    private String street;
    private String apartment;
    private String postalCode;
}
