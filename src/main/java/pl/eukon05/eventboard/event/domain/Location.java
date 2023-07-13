package pl.eukon05.eventboard.event.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public final class Location {
    private String country;
    private String city;
    private String street;
    private String apartment;
    private String postalCode;
}
